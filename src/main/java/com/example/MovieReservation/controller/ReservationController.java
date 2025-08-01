package com.example.MovieReservation.controller;
import com.example.MovieReservation.dto.ReservationRequest;
import com.example.MovieReservation.dto.ReservationResponse;
import com.example.MovieReservation.entity.SeatEntity;
import com.example.MovieReservation.security.UserPrincipal;
import com.example.MovieReservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @Valid @RequestBody ReservationRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        ReservationResponse reservation = reservationService.createReservation(request, userPrincipal.getId());
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/my-reservations")
    public ResponseEntity<Page<ReservationResponse>> getUserReservations(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ReservationResponse> reservations = reservationService.getUserReservations(userPrincipal.getId(), pageable);
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationResponse> cancelReservation(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        ReservationResponse reservation = reservationService.cancelReservation(id, userPrincipal.getId());
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/seats/{showTimeId}")
    public ResponseEntity<List<SeatEntity>> getAvailableSeats(@PathVariable Long showTimeId) {
        List<SeatEntity> seats = reservationService.getAvailableSeats(showTimeId);
        return ResponseEntity.ok(seats);
    }
}