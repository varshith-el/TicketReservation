package com.example.MovieReservation.controller;


import com.example.MovieReservation.dto.LoginRequest;
import com.example.MovieReservation.dto.SignupRequest;
import com.example.MovieReservation.entity.UserEntity;
import com.example.MovieReservation.security.JwtTokenProvider;
import com.example.MovieReservation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            UserEntity user = userService.createUser(
                    signUpRequest.getName(),
                    signUpRequest.getEmail(),
                    signUpRequest.getPassword()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("userId", user.getId());
            response.put("role", user.getRole());

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            System.out.println(">> JWT created:" + loginRequest.getEmail());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            System.out.println(">> JWT created:" + authentication);




            String jwt = tokenProvider.generateToken(authentication);
            System.out.println(jwt);
            UserEntity user = userService.getUserByEmail(loginRequest.getEmail());
            System.out.println(user);

            Map<String, Object> response = new HashMap<>();
            response.put("accessToken", jwt);
            response.put("tokenType", "Bearer");
            response.put("userId", user.getId());
            response.put("name", user.getName());
            response.put("email", user.getEmail());
            response.put("role", user.getRole());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid credentials"));
        }
    }
}