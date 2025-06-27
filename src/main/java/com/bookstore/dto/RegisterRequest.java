package com.bookstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Логин обязателен")
    @Size(min = 3, max = 50, message = "Логин должен содержать от 3 до 50 символов")
    private String username;
    
    @Email(message = "Email должен быть корректным")
    private String email;
    
    @NotBlank(message = "Пароль обязателен")
    @Size(min = 4, message = "Пароль должен содержать не менее 4 символов")
    private String password;
} 