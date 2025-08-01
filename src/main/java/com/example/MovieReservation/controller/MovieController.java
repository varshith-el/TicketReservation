package com.example.MovieReservation.controller;


import com.example.MovieReservation.dto.MovieDto;
import com.example.MovieReservation.entity.ShowTime;
import com.example.MovieReservation.service.MovieService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "*")

@Tag(name = "Movies", description = "Endpoints for movie listings and showtimes")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    @Operation(summary = "Get all movies (paginated)")
    public ResponseEntity<Page<MovieDto>> getAllMovies(@PageableDefault(size = 10) Pageable pageable) {
        Page<MovieDto> movies = movieService.getAllMovies(pageable);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get movie by ID")
    public ResponseEntity<MovieDto> getMovie(@PathVariable Long id) {
        MovieDto movie = movieService.getMovie(id);
        return ResponseEntity.ok(movie);
    }

    @Hidden
    @GetMapping("/search")
    @Operation(summary = "Search movies by title")
    public ResponseEntity<Page<MovieDto>> searchMovies(
            @RequestParam String title,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<MovieDto> movies = movieService.searchMovies(title, pageable);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}/showtimes")
    @Operation(summary = "Get showtimes for a movie on a specific date")
    public ResponseEntity<List<ShowTime>> getMovieShowTimes(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ShowTime> showTimes = movieService.getMovieShowTimes(id, date);
        return ResponseEntity.ok(showTimes);
    }

    @GetMapping("/showtimes")
    @Operation(summary = "Get all showtimes for a given date")
    public ResponseEntity<List<ShowTime>> getShowTimesByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ShowTime> showTimes = movieService.getShowTimesByDate(date);
        return ResponseEntity.ok(showTimes);
    }
}
