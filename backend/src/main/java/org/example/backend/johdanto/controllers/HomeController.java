package org.example.backend.johdanto.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private final String defaultString = "This is the main page";

    @RequestMapping("/index")
    public String index() {
        return defaultString;
    }

    @RequestMapping("/contact")
    public String contact() {
        return defaultString;
    }

}
