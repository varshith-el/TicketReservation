package com.example.MovieReservation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class ReservationResponse {
    private Long id;
    private String movieTitle;
    private LocalDateTime showDateTime;
    private String theaterName;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime createdAt;
    private Set<String> seatIdentifiers;

    // Constructors, getters, setters
    public ReservationResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }

    public LocalDateTime getShowDateTime() { return showDateTime; }
    public void setShowDateTime(LocalDateTime showDateTime) { this.showDateTime = showDateTime; }

    public String getTheaterName() { return theaterName; }
    public void setTheaterName(String theaterName) { this.theaterName = theaterName; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Set<String> getSeatIdentifiers() { return seatIdentifiers; }
    public void setSeatIdentifiers(Set<String> seatIdentifiers) { this.seatIdentifiers = seatIdentifiers; }
}
