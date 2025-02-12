package com.example.fitnessapp.service;

import com.example.fitnessapp.domain.Exercise;

import java.time.LocalDate;
import java.util.List;

public interface ExerciseService {
    List<Exercise> getAllExercises();
    List<Exercise> filterExercises(String namePart, Double minBurn, LocalDate fromDate);
    Exercise addExercise(String name, Double averageBurn, LocalDate createdOn, String imageFileName);
    Exercise getExerciseById(int id);
    void deleteExerciseById(int id);
}
