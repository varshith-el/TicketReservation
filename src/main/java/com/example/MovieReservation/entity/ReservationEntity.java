package com.example.MovieReservation.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reservations")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showtime_id", nullable = false)
    private ShowTime showTime;

    @NotNull
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private Status status = Status.CONFIRMED;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ReservationSeatEntity> reservationSeats = new HashSet<>();

    // Constructors, getters, setters
    public ReservationEntity() {}

    public ReservationEntity(UserEntity user, ShowTime showTime, BigDecimal totalPrice) {
        this.user = user;
        this.showTime = showTime;
        this.totalPrice = totalPrice;
    }

    // Getters and setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    public ShowTime getShowTime() { return showTime; }
    public void setShowTime(ShowTime showTime) { this.showTime = showTime; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Set<ReservationSeatEntity> getReservationSeats() { return reservationSeats; }
    public void setReservationSeats(Set<ReservationSeatEntity> reservationSeats) { this.reservationSeats = reservationSeats; }

    public enum Status {
        CONFIRMED, CANCELLED, COMPLETED
    }
}
