package org.example.eric.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class PublicController {

    @GetMapping("index")
    public String renderLandingPage() {
        return "index";
    }
}
