package com.example.fitnessapp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Map "/home" to return "home.html".
     */
    @GetMapping("/home")
    public String homePage() {
        return "home"; // This corresponds to home.html in src/main/resources/templates
    }

    /**
     * (Optional) Map "/" (root) to the same home page.
     * If you want the root URL to serve your home page as well, do this:
     */
    @GetMapping("/")
    public String rootRedirect() {
        // or just return "home"; if you want the same Thymeleaf template
        return "redirect:/home";
    }
}
