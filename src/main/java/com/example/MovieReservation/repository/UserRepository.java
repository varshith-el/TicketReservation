package com.example.MovieReservation.repository;

import com.example.MovieReservation.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByEmail(String email);

    Optional<UserEntity> findByName(String name);
    Boolean existsByName(String name);

    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.role = 'ADMIN'")
    long countAdmins();
}