package com.example.fitnessapp.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SessionConfig {

    @Bean
    @SessionScope
    public UserSessionHistory userSessionHistory() {
        return new UserSessionHistory();
    }

    public static class UserSessionHistory {
        private final List<VisitedPage> pages = new ArrayList<>();

        public void addPage(String url) {
            pages.add(new VisitedPage(url, LocalDateTime.now()));
        }

        public List<VisitedPage> getPages() {
            return pages;
        }
    }

    public static class VisitedPage {
        private final String url;
        private final LocalDateTime timestamp;

        public VisitedPage(String url, LocalDateTime timestamp) {
            this.url = url;
            this.timestamp = timestamp;
        }

        public String getUrl() {
            return url;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }
    }
}
