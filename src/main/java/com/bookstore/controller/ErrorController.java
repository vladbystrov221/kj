package com.bookstore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorController {
    
    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, Model model) {
        logger.error("Необработанное исключение: ", ex);
        model.addAttribute("error", "Произошла непредвиденная ошибка: " + ex.getMessage());
        return "index";
    }
    
    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(Exception ex, Model model) {
        logger.error("Страница не найдена: ", ex);
        model.addAttribute("error", "Запрошенная страница не найдена");
        return "index";
    }
} 