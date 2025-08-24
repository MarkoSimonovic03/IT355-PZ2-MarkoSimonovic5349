package com.metropolitan.IT355_PZ2_MarkoSimonovic5349.config;

import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.entity.*;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initData(
            RoleRepository roleRepository,
            UserRepository userRepository
    ) {
        return args -> {
            // 1. ROLE INIT
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));

            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));

            // 2. ADMIN USER
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setName("admin");
                admin.setUsername("admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("admin"));

                admin.setRoles(Set.of(adminRole, userRole));
                userRepository.save(admin);
            }
        };
    }
}
