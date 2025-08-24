package com.metropolitan.IT355_PZ2_MarkoSimonovic5349.controller;

import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.dto.JWTAuthResponse;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.dto.LoginDto;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.dto.RegisterDto;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.repository.UserRepository;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticate(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        return ResponseEntity.ok(response);
    }
}
