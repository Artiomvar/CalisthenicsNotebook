package com.example.fitnessapp.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "level")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "level_type", nullable = false)
    private LevelType levelType;

    @Column(name = "difficulty_index", nullable = false)
    private double difficultyIndex;

    @Column(name = "introduction_date", nullable = false)
    private LocalDate introductionDate;

    private String description;

    @ManyToMany(mappedBy = "levels")
    private List<Exercise> exercises = new ArrayList<>();

    public Level() {
    }

    public Level(int id, LevelType levelType, double difficultyIndex, LocalDate introductionDate, String description) {
        this.id = id;
        this.levelType = levelType;
        this.difficultyIndex = difficultyIndex;
        this.introductionDate = introductionDate;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public LevelType getLevelType() {
        return levelType;
    }

    public double getDifficultyIndex() {
        return difficultyIndex;
    }

    public LocalDate getIntroductionDate() {
        return introductionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLevelType(LevelType levelType) {
        this.levelType = levelType;
    }

    public void setDifficultyIndex(double difficultyIndex) {
        this.difficultyIndex = difficultyIndex;
    }

    public void setIntroductionDate(LocalDate introductionDate) {
        this.introductionDate = introductionDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        return "Level{" +
                "id=" + id +
                ", levelType=" + levelType +
                ", difficultyIndex=" + difficultyIndex +
                ", introductionDate=" + introductionDate +
                ", description='" + description + '\'' +
                '}';
    }
}
