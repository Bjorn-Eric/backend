package org.example.backend.model_view.controllers;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(Model model, @RequestParam(name = "name") String name, @RequestParam(name = "age") String age) {
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "greetings";
    }
}
