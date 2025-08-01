package com.example.MovieReservation.repository;

import com.example.MovieReservation.entity.ReservationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    @Query("SELECT r FROM ReservationEntity r WHERE r.user.id = :userId ORDER BY r.createdAt DESC")
    Page<ReservationEntity> findByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT r FROM ReservationEntity r WHERE r.user.id = :userId AND r.showTime.showDateTime > :now AND r.status = 'CONFIRMED'")
    List<ReservationEntity> findUpcomingReservationsByUserId(@Param("userId") Long userId, @Param("now") LocalDateTime now);

    @Query("SELECT SUM(r.totalPrice) FROM ReservationEntity r WHERE r.status = 'CONFIRMED' AND CAST(r.createdAt AS DATE) = CURRENT_DATE")
    BigDecimal getTodayRevenue();

    @Query("SELECT COUNT(r) FROM ReservationEntity r WHERE r.status = 'CONFIRMED' AND CAST(r.createdAt AS DATE) = CURRENT_DATE")
    long getTodayReservationCount();

    @Query("SELECT r FROM ReservationEntity r WHERE r.showTime.id = :showTimeId AND r.status = 'CONFIRMED'")
    List<ReservationEntity> findConfirmedReservationsByShowTimeId(@Param("showTimeId") Long showTimeId);
}