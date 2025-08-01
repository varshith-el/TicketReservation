package com.example.MovieReservation.repository;


import com.example.MovieReservation.entity.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {

    @Query("SELECT st FROM ShowTime st WHERE st.movie.id = :movieId AND st.showDateTime >= :start AND st.showDateTime < :end ORDER BY st.showDateTime")
    List<ShowTime> findByMovieIdAndDate(@Param("movieId") Long movieId,
                                        @Param("start") LocalDateTime start,
                                        @Param("end") LocalDateTime end);

    @Query("SELECT st FROM ShowTime st WHERE st.showDateTime >= :start AND st.showDateTime < :end ORDER BY st.showDateTime")
    List<ShowTime> findByDate(@Param("start") LocalDateTime start,
                              @Param("end") LocalDateTime end);

    @Query("SELECT st FROM ShowTime st WHERE st.showDateTime >= :startTime AND st.showDateTime < :endTime")
    List<ShowTime> findByDateTimeBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT COUNT(r) FROM ReservationEntity r WHERE r.showTime.id = :showTimeId AND r.status = 'CONFIRMED'")
    long countReservedSeats(@Param("showTimeId") Long showTimeId);
}
