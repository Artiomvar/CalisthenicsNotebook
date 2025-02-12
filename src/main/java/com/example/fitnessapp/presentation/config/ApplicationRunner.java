package com.example.fitnessapp.presentation.config;

import com.example.fitnessapp.domain.DataFactory;
import com.example.fitnessapp.presentation.presenter.Presenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Profile("!jpa") // Only run this runner when not in JPA profile
public class ApplicationRunner implements CommandLineRunner {

    private final Presenter presenter;

    @Autowired
    public ApplicationRunner(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void run(String... args) {
        // Seed data only for non-JPA profiles
        DataFactory.seed();
        presenter.start();
    }
}
