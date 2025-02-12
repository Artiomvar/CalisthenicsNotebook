package com.example.fitnessapp.web.config;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PageVisitInterceptor implements HandlerInterceptor {

    private final SessionConfig.UserSessionHistory userSessionHistory;

    public PageVisitInterceptor(SessionConfig.UserSessionHistory userSessionHistory) {
        this.userSessionHistory = userSessionHistory;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        userSessionHistory.addPage(request.getRequestURI());
        return true;
    }
}
