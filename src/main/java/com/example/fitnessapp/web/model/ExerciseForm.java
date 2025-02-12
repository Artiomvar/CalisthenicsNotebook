package com.example.fitnessapp.web.model;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class ExerciseForm {

    @NotBlank(message = "{form.name.notBlank}")
    private String name;

    @NotNull(message = "{form.averageBurn.notNull}")
    @Positive(message = "{form.averageBurn.positive}")
    private Double averageBurn;

    @NotNull(message = "{form.createdOn.notNull}")
    @PastOrPresent(message = "{form.createdOn.pastOrPresent}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdOn;

    @NotBlank(message = "{form.imageFileName.notBlank}")
    private String imageFileName;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getAverageBurn() {
        return averageBurn;
    }
    public void setAverageBurn(Double averageBurn) {
        this.averageBurn = averageBurn;
    }
    public LocalDate getCreatedOn() {
        return createdOn;
    }
    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }
    public String getImageFileName() {
        return imageFileName;
    }
    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
