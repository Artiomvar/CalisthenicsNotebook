package com.example.fitnessapp.web.controller;

import com.example.fitnessapp.web.config.SessionConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionHistoryController {

    @Autowired
    private SessionConfig.UserSessionHistory userSessionHistory;

    @GetMapping("/session-history")
    public String sessionHistory(Model model) {
        model.addAttribute("pages", userSessionHistory.getPages());
        return "session-history";
    }
}
