package com.example.MovieReservation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "seats", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"showtime_id", "row_name", "seat_number"})
})
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "row_name")
    private String rowName;

    @NotNull
    @Column(name = "seat_number")
    private Integer seatNumber;

    @Column(name = "is_available")
    private Boolean isAvailable = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showtime_id", nullable = false)
    @JsonIgnore
    private ShowTime showTime;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ReservationSeatEntity> reservationSeats = new HashSet<>();

    // Constructors, getters, setters
    public SeatEntity() {}

    public SeatEntity(String rowName, Integer seatNumber, ShowTime showTime) {
        this.rowName = rowName;
        this.seatNumber = seatNumber;
        this.showTime = showTime;
    }

    // Getters and setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRowName() { return rowName; }
    public void setRowName(String rowName) { this.rowName = rowName; }

    public Integer getSeatNumber() { return seatNumber; }
    public void setSeatNumber(Integer seatNumber) { this.seatNumber = seatNumber; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }

    public ShowTime getShowTime() { return showTime; }
    public void setShowTime(ShowTime showTime) { this.showTime = showTime; }

    public Set<ReservationSeatEntity> getReservationSeats() { return reservationSeats; }
    public void setReservationSeats(Set<ReservationSeatEntity> reservationSeats) { this.reservationSeats = reservationSeats; }

    public String getSeatIdentifier() {
        return rowName + seatNumber;
    }
}