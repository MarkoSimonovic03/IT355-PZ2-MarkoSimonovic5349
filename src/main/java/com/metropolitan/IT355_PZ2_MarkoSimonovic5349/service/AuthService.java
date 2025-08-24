package com.metropolitan.IT355_PZ2_MarkoSimonovic5349.service;

import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.dto.LoginDto;
import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);

}
