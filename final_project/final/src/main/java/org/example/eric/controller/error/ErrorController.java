package org.example.eric.controller.error;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = Exception.class)
    public String showErrorPage(Model model, Exception e) {
        System.out.println("Error: " + e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "error";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(Model model, Exception e) {
        model.addAttribute("errorMessage", "The page doesn't exist");
        return "error";
    }
}
