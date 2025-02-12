package com.example.fitnessapp.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(name = "average_burn", nullable = false)
    private double averageBurn;

    @Column(name = "created_on", nullable = false)
    private LocalDate createdOn;

    @Column(name = "image_file_name")
    private String imageFileName;

    @ManyToMany
    @JoinTable(
            name = "exercise_musclegroup",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "musclegroup_id")
    )
    private Set<MuscleGroup> muscleGroups = new HashSet<>();

    /**
     * If you are referencing ex1.addLevel(...) in DataFactory, you need a
     * Many-to-Many or One-to-Many between Exercise and Level. Below is a
     * Many-to-Many example. Also ensure your database has a table e.g. 'exercise_level'.
     */
    @ManyToMany
    @JoinTable(
            name = "exercise_level",  // The table name for linking exercises <-> levels
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "level_id")
    )
    private List<Level> levels = new ArrayList<>();

    public Exercise() {
    }

    public Exercise(int id, String name, double averageBurn, LocalDate createdOn) {
        this.id = id;
        this.name = name;
        this.averageBurn = averageBurn;
        this.createdOn = createdOn;
    }

    // ---------------- Getters & Setters ----------------

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAverageBurn() {
        return averageBurn;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public Set<MuscleGroup> getMuscleGroups() {
        return muscleGroups;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAverageBurn(double averageBurn) {
        this.averageBurn = averageBurn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public void setMuscleGroups(Set<MuscleGroup> muscleGroups) {
        this.muscleGroups = muscleGroups;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    // ---------------- Utility Methods ----------------

    /** Add a single MuscleGroup to the set of muscleGroups. */
    public void addMuscleGroup(MuscleGroup mg) {
        this.muscleGroups.add(mg);
    }

    /**
     * Add a single Level to the list of levels.
     * This is required if DataFactory or your code calls ex.addLevel(...)
     */
    public void addLevel(Level level) {
        this.levels.add(level);
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", averageBurn=" + averageBurn +
                ", createdOn=" + createdOn +
                ", imageFileName='" + imageFileName + '\'' +
                '}';
    }
}
