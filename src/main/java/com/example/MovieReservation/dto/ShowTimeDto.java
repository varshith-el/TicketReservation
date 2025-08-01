package com.example.MovieReservation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ShowTimeDto {
    private Long id;
    private LocalDateTime showDateTime;
    private BigDecimal price;
    private String theaterName;
    private Integer totalSeats;

    // Nested movie details
    private Long movieId;
    private String movieTitle;
    private String moviePosterUrl;
    private String movieGenre;
    private Integer movieDuration;

    public ShowTimeDto() {
    }

    public ShowTimeDto(Long id, LocalDateTime showDateTime, BigDecimal price, String theaterName, Integer totalSeats,
                       Long movieId, String movieTitle, String moviePosterUrl, String movieGenre, Integer movieDuration) {
        this.id = id;
        this.showDateTime = showDateTime;
        this.price = price;
        this.theaterName = theaterName;
        this.totalSeats = totalSeats;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.moviePosterUrl = moviePosterUrl;
        this.movieGenre = movieGenre;
        this.movieDuration = movieDuration;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getShowDateTime() {
        return showDateTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public Long getMovieId() {
        return movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMoviePosterUrl() {
        return moviePosterUrl;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public Integer getMovieDuration() {
        return movieDuration;
    }

}
