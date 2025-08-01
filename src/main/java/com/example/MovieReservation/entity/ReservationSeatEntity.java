package com.example.MovieReservation.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "reservation_seats")
public class ReservationSeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationEntity reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private SeatEntity seat;

    // Constructors, getters, setters
    public ReservationSeatEntity() {
    }

    public ReservationSeatEntity(ReservationEntity reservation, SeatEntity seat) {
        this.reservation = reservation;
        this.seat = seat;
    }

    // Getters and setters...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationEntity getReservation() {
        return reservation;
    }

    public void setReservation(ReservationEntity reservation) {
        this.reservation = reservation;
    }

    public SeatEntity getSeat() {
        return seat;
    }

    public void setSeat(SeatEntity seat) {
        this.seat = seat;
    }

}