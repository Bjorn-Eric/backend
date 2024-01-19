package org.example.backend.johdanto.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(@RequestParam(name = "location") String location, @RequestParam(name = "name") String name){
        return "Welcome to the " + location + " " + name + "!";
    }
}
