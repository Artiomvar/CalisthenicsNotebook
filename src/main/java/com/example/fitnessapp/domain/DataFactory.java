// src/main/java/com/example/fitnessapp/domain/DataFactory.java
package com.example.fitnessapp.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataFactory {
    public static List<MuscleGroup> muscleGroups = new ArrayList<>();
    public static List<Exercise> exercises = new ArrayList<>();

    // We'll store levels in a list as well
    public static List<Level> levels = new ArrayList<>();

    public static void seed() {
        // Seed MuscleGroups
        muscleGroups.clear();
        muscleGroups.add(new MuscleGroup(1, "Chest", "Focus on pectoral muscles", LocalDate.of(2020,1,10), 1.5));
        muscleGroups.add(new MuscleGroup(2, "Back", "Focus on latissimus dorsi and traps", LocalDate.of(2020,2,5), 1.8));
        muscleGroups.add(new MuscleGroup(3, "Legs", "Focus on quadriceps and hamstrings", LocalDate.of(2020,3,15), 2.0));
        muscleGroups.add(new MuscleGroup(4, "Shoulders", "Focus on deltoids", LocalDate.of(2020,4,20), 1.2));

        // Seed Levels
        levels.clear();
        levels.add(new Level(1, LevelType.BEGINNER, 1.0, LocalDate.of(2021,1,1), "Suitable for newbies"));
        levels.add(new Level(2, LevelType.INTERMEDIATE, 2.0, LocalDate.of(2021,3,1), "For those with some experience"));
        levels.add(new Level(3, LevelType.ADVANCED, 3.0, LocalDate.of(2021,5,1), "For experienced athletes"));
        levels.add(new Level(4, LevelType.BEGINNER, 1.2, LocalDate.of(2021,6,10), "Slightly harder beginner level"));

        // Seed Exercises
        exercises.clear();
        Exercise ex1 = new Exercise(1, "Push-up", 50.0, LocalDate.of(2022,1,1));
        ex1.addMuscleGroup(muscleGroups.get(0)); // chest
        ex1.addLevel(levels.get(0)); // beginner
        ex1.addLevel(levels.get(1)); // intermediate

        Exercise ex2 = new Exercise(2, "Squat", 70.0, LocalDate.of(2022,1,5));
        ex2.addMuscleGroup(muscleGroups.get(2)); // legs
        ex2.addLevel(levels.get(0)); // beginner
        ex2.addLevel(levels.get(2)); // advanced

        Exercise ex3 = new Exercise(3, "Pull-up", 80.0, LocalDate.of(2022,2,10));
        ex3.addMuscleGroup(muscleGroups.get(1)); // back
        ex3.addLevel(levels.get(1)); // intermediate
        ex3.addLevel(levels.get(2)); // advanced

        Exercise ex4 = new Exercise(4, "Shoulder Press", 60.0, LocalDate.of(2022,3,15));
        ex4.addMuscleGroup(muscleGroups.get(3)); // shoulders
        ex4.addLevel(levels.get(0)); // beginner
        ex4.addLevel(levels.get(3)); // another beginner variant

        exercises.add(ex1);
        exercises.add(ex2);
        exercises.add(ex3);
        exercises.add(ex4);
    }
}
