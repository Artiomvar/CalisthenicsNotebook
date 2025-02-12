package com.example.fitnessapp.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "musclegroup")
public class MuscleGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "established_on", nullable = false)
    private LocalDate establishedOn;

    @Column(name = "complexity_factor", nullable = false)
    private double complexityFactor;

    @Column(name = "image_file_name")
    private String imageFileName;

    // EAGER fetch ensures we can see exercises outside a transaction
    @ManyToMany(mappedBy = "muscleGroups", fetch = FetchType.EAGER)
    private Set<Exercise> exercises = new HashSet<>();

    public MuscleGroup() {
    }

    public MuscleGroup(int id, String name, String description,
                       LocalDate establishedOn, double complexityFactor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.establishedOn = establishedOn;
        this.complexityFactor = complexityFactor;
    }

    // ----------- Getters & Setters -------------
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

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

    public double getComplexityFactor() {
        return complexityFactor;
    }
    public void setComplexityFactor(double complexityFactor) {
        this.complexityFactor = complexityFactor;
    }

    public String getImageFileName() {
        return imageFileName;
    }
    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }
    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void addExercise(Exercise ex) {
        this.exercises.add(ex);
    }

    @Override
    public String toString() {
        return "MuscleGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", establishedOn=" + establishedOn +
                ", complexityFactor=" + complexityFactor +
                '}';
    }
}
