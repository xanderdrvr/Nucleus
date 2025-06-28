package com.nucleus.service;

import com.nucleus.dto.RegisterRequest;
import com.nucleus.entity.User;
import com.nucleus.exception.user.UserAlreadyExistsException;
import com.nucleus.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    private String generateRandomAvatarUrl(String seed) {
        String encodedSeed = URLEncoder.encode(seed, StandardCharsets.UTF_8);
        return "https://api.dicebear.com/9.x/thumbs/svg?seed=" + encodedSeed + "&backgroundColor=transparent&radius=50&size=100&scale=100&style=circle";
    }
}
