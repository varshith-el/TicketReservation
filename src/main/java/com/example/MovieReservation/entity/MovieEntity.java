package com.example.MovieReservation.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 200)
    private String title;

    @Size(max = 1000)
    private String description;

    @Size(max = 500)
    @Column(name = "poster_url")
    private String posterUrl;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Genre genre;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ShowTime> showTimes = new HashSet<>();

    // Constructors, getters, setters
    public MovieEntity() {}

    public MovieEntity(String title, String description, String posterUrl, Genre genre, Integer durationMinutes) {
        this.title = title;
        this.description = description;
        this.posterUrl = posterUrl;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
    }

    // Getters and setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Set<ShowTime> getShowTimes() { return showTimes; }
    public void setShowTimes(Set<ShowTime> showTimes) { this.showTimes = showTimes; }

    public enum Genre {
        ACTION, COMEDY, DRAMA, HORROR, ROMANCE, SCI_FI, THRILLER, ANIMATED, DOCUMENTARY
    }
}
