package com.bookstore.service;

import com.bookstore.dto.AuthRequest;
import com.bookstore.dto.AuthResponse;
import com.bookstore.dto.RegisterRequest;
import com.bookstore.entity.User;
import com.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    
    public String authenticate(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );
            
            User user = (User) authentication.getPrincipal();
            return jwtService.generateToken(user);
        } catch (AuthenticationException e) {
            logger.error("Ошибка аутентификации: {}", e.getMessage());
            throw new IllegalArgumentException("Неверные учетные данные");
        }
    }
    
    @Transactional
    public String register(String username, String email, String password) {
        // Создаем пользователя
        User user = userService.createUser(username, email, password);
        return jwtService.generateToken(user);
    }
    
    // Методы для веб-интерфейса
    public AuthResponse login(AuthRequest authRequest) {
        try {
            String token = authenticate(authRequest.getUsername(), authRequest.getPassword());
            User user = userService.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
            
            return new AuthResponse(token, authRequest.getUsername(), user.getRole().name());
        } catch (Exception e) {
            logger.error("Ошибка входа: {}", e.getMessage());
            throw new IllegalArgumentException("Неверные учетные данные");
        }
    }
    
    public AuthResponse register(RegisterRequest registerRequest) {
        try {
            String token = register(registerRequest.getUsername(), registerRequest.getEmail(), registerRequest.getPassword());
            
            User user = userService.findByUsername(registerRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден после регистрации"));
                
            return new AuthResponse(token, registerRequest.getUsername(), user.getRole().name());
        } catch (Exception e) {
            logger.error("Ошибка регистрации: {}", e.getMessage());
            throw new IllegalArgumentException("Ошибка регистрации: " + e.getMessage());
        }
    }
} 