package com.example.fitnessapp.repository;

import com.example.fitnessapp.domain.Exercise;
import java.time.LocalDate;
import java.util.List;

public interface ExerciseRepository {
    List<Exercise> findAll();
    List<Exercise> findByCriteria(String namePart, Double minBurn, LocalDate fromDate);
    Exercise findById(int id);
    Exercise save(Exercise exercise);
    void deleteById(int id);
}
