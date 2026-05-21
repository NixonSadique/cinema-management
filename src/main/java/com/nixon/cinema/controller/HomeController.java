package com.nixon.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String redirectToDocumentation() {
        return "redirect:/swagger-ui/index.html";
    }
}
