package com.nucleus.service;

import com.nucleus.dto.AuthRequest;
import com.nucleus.dto.AuthResponse;
import com.nucleus.dto.RegisterRequest;
import com.nucleus.entity.User;
import com.nucleus.exception.user.InvalidCredentialsException;
import com.nucleus.exception.user.UserAlreadyExistsException;
import com.nucleus.exception.user.UserNotFoundException;
import com.nucleus.repository.UserRepository;
import com.nucleus.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("Email already in use: " + request.email());
        }

        User user = new User(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.displayName(),
                generateRandomAvatarUrl(request.email())
        );

        userRepository.save(user);
    }

    public AuthResponse login(AuthRequest authRequest) {
        User user = userRepository.findByEmail(authRequest.email())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + authRequest.email()));

        if (!passwordEncoder.matches(authRequest.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(
                token,
                user.getDisplayName(),
                user.getAvatarUrl()
        );
    }

    private String generateRandomAvatarUrl(String seed) {
        String encodedSeed = URLEncoder.encode(seed, StandardCharsets.UTF_8);
        return "https://api.dicebear.com/9.x/thumbs/svg?seed=" + encodedSeed + "&backgroundColor=transparent&radius=50&size=100&scale=100&style=circle";
    }
}
