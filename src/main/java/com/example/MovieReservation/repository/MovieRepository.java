package com.example.MovieReservation.repository;



import com.example.MovieReservation.entity.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    List<MovieEntity> findByGenre(MovieEntity.Genre genre);

    @Query("SELECT DISTINCT m FROM MovieEntity m JOIN m.showTimes st WHERE CAST(st.showDateTime AS DATE) = CURRENT_DATE")
    List<MovieEntity> findMoviesWithTodayShowTimes();

    @Query("SELECT m FROM MovieEntity m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<MovieEntity> findByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);
}
