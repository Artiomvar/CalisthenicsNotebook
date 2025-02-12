package com.example.fitnessapp.web.model;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class MuscleGroupForm {
    @NotBlank(message = "{form.name.notBlank}")
    private String name;

    @NotBlank(message = "{form.description.notBlank}")
    private String description;

    @NotNull(message = "{form.establishedOn.notNull}")
    @PastOrPresent(message = "{form.establishedOn.pastOrPresent}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate establishedOn;

    @NotNull(message = "{form.complexityFactor.notNull}")
    @Positive(message = "{form.complexityFactor.positive}")
    private Double complexityFactor;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDate getEstablishedOn() {
        return establishedOn;
    }
    public void setEstablishedOn(LocalDate establishedOn) {
        this.establishedOn = establishedOn;
    }
    public Double getComplexityFactor() {
        return complexityFactor;
    }
    public void setComplexityFactor(Double complexityFactor) {
        this.complexityFactor = complexityFactor;
    }

}
