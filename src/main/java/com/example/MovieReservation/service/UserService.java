package com.example.MovieReservation.service;


import com.example.MovieReservation.entity.UserEntity;
import com.example.MovieReservation.exception.ResourceNotFoundException;
import com.example.MovieReservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity createUser(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        UserEntity user = new UserEntity(name, email, passwordEncoder.encode(password));

        // Make first user an admin
        if (userRepository.count() == 0) {
            user.setRole(UserEntity.Role.ADMIN);
        }

        return userRepository.save(user);
    }

    public UserEntity promoteToAdmin(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setRole(UserEntity.Role.ADMIN);
        return userRepository.save(user);
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}