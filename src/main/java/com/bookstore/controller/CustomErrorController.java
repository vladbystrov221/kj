package com.bookstore.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class CustomErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        Object path = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        
        if (exception != null) {
            logger.error("Ошибка при обработке запроса: {}", exception);
        }
        
        model.addAttribute("timestamp", new Date());
        model.addAttribute("path", path);
        
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("status", statusCode);
            
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            }
            
            if (message != null && !message.toString().isEmpty()) {
                model.addAttribute("message", message);
            } else {
                switch (statusCode) {
                    case 400:
                        model.addAttribute("message", "Некорректный запрос");
                        break;
                    case 403:
                        model.addAttribute("message", "Доступ запрещен");
                        break;
                    case 500:
                        model.addAttribute("message", "Внутренняя ошибка сервера");
                        break;
                    default:
                        model.addAttribute("message", "Произошла ошибка");
                        break;
                }
            }
        } else {
            model.addAttribute("status", 500);
            model.addAttribute("message", "Неизвестная ошибка");
        }
        
        return "error";
    }
} 