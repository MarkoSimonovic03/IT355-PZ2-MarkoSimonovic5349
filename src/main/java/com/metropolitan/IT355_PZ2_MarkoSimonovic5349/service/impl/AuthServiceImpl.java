package com.metropolitan.IT355_PZ2_MarkoSimonovic5349.service.impl;

import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.dto.LoginDto;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.dto.RegisterDto;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.entity.Role;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.entity.User;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.repository.RoleRepository;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.repository.UserRepository;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.security.JwtTokenProvider;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new RuntimeException("Email is already taken!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));
        user.setRoles(Set.of(userRole));

        userRepository.save(user);

        return "User registered successfully!";
    }

}
