package com.example.tech_card_storage.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleError(Model model, Exception ex) {
        model.addAttribute("message", "Возникла непредвиденная ошибка: " + ex.getMessage());
        return "error-page.html";
    }
}
