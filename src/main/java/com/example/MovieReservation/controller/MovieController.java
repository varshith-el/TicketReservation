package com.example.MovieReservation.controller;


import com.example.MovieReservation.dto.MovieDto;
import com.example.MovieReservation.entity.MovieEntity;
import com.example.MovieReservation.entity.ShowTime;
import com.example.MovieReservation.service.MovieService;
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
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<Page<MovieDto>> getAllMovies(@PageableDefault(size = 10) Pageable pageable) {
        Page<MovieDto> movies = movieService.getAllMovies(pageable);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable Long id) {
        MovieDto movie = movieService.getMovie(id);
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MovieDto>> searchMovies(
            @RequestParam String title,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<MovieDto> movies = movieService.searchMovies(title, pageable);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}/showtimes")
    public ResponseEntity<List<ShowTime>> getMovieShowTimes(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ShowTime> showTimes = movieService.getMovieShowTimes(id, date);
        return ResponseEntity.ok(showTimes);
    }

    @GetMapping("/showtimes")
    public ResponseEntity<List<ShowTime>> getShowTimesByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ShowTime> showTimes = movieService.getShowTimesByDate(date);
        return ResponseEntity.ok(showTimes);
    }

    @PostMapping()
    public ResponseEntity<String> addMovie(@RequestBody MovieDto moviedto){
        movieService.createMovie(moviedto);
        return ResponseEntity.ok("Movie Created");
    }
}
