package com.example.MovieReservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class MovieDto {
    private Long id;

    @NotBlank
    @Size(max = 200)
    private String title;

    @Size(max = 1000)
    private String description;

    private String posterUrl;

    @NotNull
    private String genre;

    private Integer durationMinutes;
    private LocalDateTime createdAt;

    // Constructors, getters, setters
    public MovieDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}