package com.example.MovieReservation.service;

import com.example.MovieReservation.dto.ReservationRequest;
import com.example.MovieReservation.dto.ReservationResponse;
import com.example.MovieReservation.entity.*;
import com.example.MovieReservation.exception.ResourceNotFoundException;
import com.example.MovieReservation.exception.SeatAlreadyBookedException;
import com.example.MovieReservation.repository.ReservationRepository;
import com.example.MovieReservation.repository.SeatRepository;
import com.example.MovieReservation.repository.ShowTimeRepository;
import com.example.MovieReservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatLockService seatLockService;

    public ReservationResponse createReservation(ReservationRequest request, Long userId) {
        // Validate user
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Validate showtime
        ShowTime showTime = showTimeRepository.findById(request.getShowTimeId())
                .orElseThrow(() -> new ResourceNotFoundException("ShowTime not found"));

        // Validate seats exist and are for the correct showtime
        List<SeatEntity> requestedSeats = seatRepository.findAllById(request.getSeatIds());
        if (requestedSeats.size() != request.getSeatIds().size()) {
            throw new ResourceNotFoundException("One or more seats not found");
        }

        // Verify all seats belong to the requested showtime
        boolean allSeatsValid = requestedSeats.stream()
                .allMatch(seat -> seat.getShowTime().getId().equals(request.getShowTimeId()));
        if (!allSeatsValid) {
            throw new IllegalArgumentException("All seats must belong to the selected showtime");
        }

        // Try to acquire locks on seats
        String lockUserId = "user_" + userId;
        if (!seatLockService.lockSeats(request.getShowTimeId(), request.getSeatIds(), lockUserId)) {
            throw new SeatAlreadyBookedException("One or more seats are currently being booked by another user");
        }

        try {
            // Re-fetch seats with pessimistic lock to ensure consistency
            List<SeatEntity> seatsWithLock = seatRepository.findByIdInWithLock(request.getSeatIds());

            // Check if any seat is no longer available
            List<SeatEntity> unavailableSeats = seatsWithLock.stream()
                    .filter(seat -> !seat.getIsAvailable())
                    .collect(Collectors.toList());

            if (!unavailableSeats.isEmpty()) {
                throw new SeatAlreadyBookedException("Some seats are no longer available");
            }

            // Calculate total price
            BigDecimal totalPrice = showTime.getPrice().multiply(new BigDecimal(seatsWithLock.size()));

            // Create reservation
            ReservationEntity reservation = new ReservationEntity(user, showTime, totalPrice);
            reservation = reservationRepository.save(reservation);

            // Mark seats as unavailable and create reservation-seat relationships
            for (SeatEntity seat : seatsWithLock) {
                seat.setIsAvailable(false);
                ReservationSeatEntity reservationSeat = new ReservationSeatEntity(reservation, seat);
                reservation.getReservationSeats().add(reservationSeat);
            }

            reservationRepository.save(reservation);

            return convertToReservationResponse(reservation);

        } finally {
            // Always release locks
            seatLockService.unlockSeats(request.getShowTimeId(), request.getSeatIds(), lockUserId);
        }
    }

    public Page<ReservationResponse> getUserReservations(Long userId, Pageable pageable) {
        Page<ReservationEntity> reservations = reservationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        return reservations.map(this::convertToReservationResponse);
    }

    public ReservationResponse cancelReservation(Long reservationId, Long userId) {
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        // Check if user owns this reservation
        if (!reservation.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("You can only cancel your own reservations");
        }

        // Check if showtime is in the future
        if (reservation.getShowTime().getShowDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot cancel past reservations");
        }

        // Mark reservation as cancelled
        reservation.setStatus(ReservationEntity.Status.CANCELLED);

        // Mark seats as available again
        for (ReservationSeatEntity reservationSeat : reservation.getReservationSeats()) {
            reservationSeat.getSeat().setIsAvailable(true);
        }

        reservationRepository.save(reservation);
        return convertToReservationResponse(reservation);
    }

    public List<SeatEntity> getAvailableSeats(Long showTimeId) {
        ShowTime showTime = showTimeRepository.findById(showTimeId)
                .orElseThrow(() -> new ResourceNotFoundException("ShowTime not found"));

        return seatRepository.findAvailableSeatsByShowTimeId(showTimeId);
    }

    private ReservationResponse convertToReservationResponse(ReservationEntity reservation) {
        ReservationResponse response = new ReservationResponse();
        response.setId(reservation.getId());
        response.setMovieTitle(reservation.getShowTime().getMovie().getTitle());
        response.setShowDateTime(reservation.getShowTime().getShowDateTime());
        response.setTheaterName(reservation.getShowTime().getTheaterName());
        response.setTotalPrice(reservation.getTotalPrice());
        response.setStatus(reservation.getStatus().toString());
        response.setCreatedAt(reservation.getCreatedAt());

        Set<String> seatIdentifiers = reservation.getReservationSeats().stream()
                .map(rs -> rs.getSeat().getSeatIdentifier())
                .collect(Collectors.toSet());
        response.setSeatIdentifiers(seatIdentifiers);

        return response;
    }
}
