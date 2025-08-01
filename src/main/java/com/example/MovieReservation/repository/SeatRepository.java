package com.example.MovieReservation.repository;


import com.example.MovieReservation.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {

    @Query("SELECT s FROM SeatEntity s WHERE s.showTime.id = :showTimeId ORDER BY s.rowName, s.seatNumber")
    List<SeatEntity> findByShowTimeIdOrderByRowNameAndSeatNumber(@Param("showTimeId") Long showTimeId);

    @Query("SELECT s FROM SeatEntity s WHERE s.showTime.id = :showTimeId AND s.isAvailable = true")
    List<SeatEntity> findAvailableSeatsByShowTimeId(@Param("showTimeId") Long showTimeId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM SeatEntity s WHERE s.id IN :seatIds")
    List<SeatEntity> findByIdInWithLock(@Param("seatIds") List<Long> seatIds);

    @Query("SELECT s FROM SeatEntity s WHERE s.showTime.id = :showTimeId AND s.rowName = :rowName AND s.seatNumber = :seatNumber")
    Optional<SeatEntity> findByShowTimeAndRowAndNumber(@Param("showTimeId") Long showTimeId,
                                                 @Param("rowName") String rowName,
                                                 @Param("seatNumber") Integer seatNumber);
}
