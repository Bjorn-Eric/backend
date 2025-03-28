package org.example.eric.controller.error;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(Model model, Exception e) {
        System.out.println("NoHandlerFoundException");
        model.addAttribute("random", "The page doesn't exist");
        return "error";
    }
}
