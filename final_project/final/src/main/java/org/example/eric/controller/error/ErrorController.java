package org.example.eric.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error")
    public String showErrorPage() {
        return "error";
    }

//    @GetMapping("/accessDenied")
//    public String showAccessDeniedPage() {
//        return "accessDenied";
//    }

}
