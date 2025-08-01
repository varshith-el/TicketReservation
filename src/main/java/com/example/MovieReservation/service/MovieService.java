package com.example.MovieReservation.service;

import com.example.MovieReservation.dto.MovieDto;
import com.example.MovieReservation.entity.MovieEntity;
import com.example.MovieReservation.entity.ShowTime;
import com.example.MovieReservation.exception.ResourceNotFoundException;
import com.example.MovieReservation.repository.MovieRepository;
import com.example.MovieReservation.repository.ShowTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ShowTimeRepository showTimeRepository;

    public MovieDto createMovie(MovieDto movieDto) {
        MovieEntity movie = convertToEntity(movieDto);
        movie = movieRepository.save(movie);
        return convertToDto(movie);
    }

    public MovieDto updateMovie(Long id, MovieDto movieDto) {
        MovieEntity movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        movie.setPosterUrl(movieDto.getPosterUrl());
        movie.setGenre(MovieEntity.Genre.valueOf(movieDto.getGenre()));
        movie.setDurationMinutes(movieDto.getDurationMinutes());

        movie = movieRepository.save(movie);
        return convertToDto(movie);
    }

    public void deleteMovie(Long id) {
        MovieEntity movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        movieRepository.delete(movie);
    }

    public MovieDto getMovie(Long id) {
        MovieEntity movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        return convertToDto(movie);
    }

    public Page<MovieDto> getAllMovies(Pageable pageable) {
        Page<MovieEntity> movies = movieRepository.findAll(pageable);
        return movies.map(this::convertToDto);
    }

    public Page<MovieDto> searchMovies(String title, Pageable pageable) {
        Page<MovieEntity> movies = movieRepository.findByTitleContainingIgnoreCase(title, pageable);
        return movies.map(this::convertToDto);
    }

    public List<ShowTime> getMovieShowTimes(Long movieId, LocalDate date) {
        movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

        return showTimeRepository.findByMovieIdAndDate(movieId, date);
    }

    public List<ShowTime> getShowTimesByDate(LocalDate date) {
        return showTimeRepository.findByDate(date);
    }

    private MovieDto convertToDto(MovieEntity movie) {
        MovieDto dto = new MovieDto();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        dto.setPosterUrl(movie.getPosterUrl());
        dto.setGenre(movie.getGenre().toString());
        dto.setDurationMinutes(movie.getDurationMinutes());
        dto.setCreatedAt(movie.getCreatedAt());
        return dto;
    }

    private MovieEntity convertToEntity(MovieDto dto) {
        MovieEntity movie = new MovieEntity();
        movie.setTitle(dto.getTitle());
        movie.setDescription(dto.getDescription());
        movie.setPosterUrl(dto.getPosterUrl());
        movie.setGenre(MovieEntity.Genre.valueOf(dto.getGenre()));
        movie.setDurationMinutes(dto.getDurationMinutes());
        return movie;
    }
}
