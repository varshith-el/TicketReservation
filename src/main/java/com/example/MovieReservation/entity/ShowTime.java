package com.example.MovieReservation.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "showtimes")
public class ShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movie;

    @NotNull
    @Column(name = "show_datetime")
    private LocalDateTime showDateTime;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @Column(name = "theater_name")
    private String theaterName;

    @Column(name = "total_seats")
    private Integer totalSeats = 100; // Default theater capacity

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "showTime", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<SeatEntity> seats = new HashSet<>();

    

    @OneToMany(mappedBy = "showTime", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ReservationEntity> reservations = new HashSet<>();

    // Constructors, getters, setters
    public ShowTime() {}

    public ShowTime(MovieEntity movie, LocalDateTime showDateTime, BigDecimal price, String theaterName) {
        this.movie = movie;
        this.showDateTime = showDateTime;
        this.price = price;
        this.theaterName = theaterName;
        initializeSeats();
    }

    private void initializeSeats() {
        this.seats = new HashSet<>();
        for (int row = 1; row <= 10; row++) {
            for (int number = 1; number <= 10; number++) {
                char rowLetter = (char) ('A' + row - 1);
                SeatEntity seat = new SeatEntity(String.valueOf(rowLetter), number, this);
                this.seats.add(seat);
            }
        }
    }

    // Getters and setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public MovieEntity getMovie() { return movie; }
    public void setMovie(MovieEntity movie) { this.movie = movie; }

    public LocalDateTime getShowDateTime() { return showDateTime; }
    public void setShowDateTime(LocalDateTime showDateTime) { this.showDateTime = showDateTime; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getTheaterName() { return theaterName; }
    public void setTheaterName(String theaterName) { this.theaterName = theaterName; }

    public Integer getTotalSeats() { return totalSeats; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }


    public LocalDateTime getCreatedAt() {

        return createdAt; }

    public Set<SeatEntity> getSeats() { return seats; }
    public void setSeats(Set<SeatEntity> seats) { this.seats = seats; }

    public Set<ReservationEntity> getReservations() { return reservations; }
    public void setReservations(Set<ReservationEntity> reservations) { this.reservations = reservations; }
}
