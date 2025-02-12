package com.example.fitnessapp.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final SessionConfig.UserSessionHistory userSessionHistory;

    @Autowired
    public WebConfig(SessionConfig.UserSessionHistory userSessionHistory) {
        this.userSessionHistory = userSessionHistory;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PageVisitInterceptor(userSessionHistory))
                .addPathPatterns("/**");
    }
}
