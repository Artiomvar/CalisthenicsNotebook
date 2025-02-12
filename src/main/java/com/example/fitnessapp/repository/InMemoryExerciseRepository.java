package com.example.fitnessapp.repository;

import com.example.fitnessapp.domain.DataFactory;
import com.example.fitnessapp.domain.Exercise;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Profile("inmemory")
public class InMemoryExerciseRepository implements ExerciseRepository {

    @Override
    public List<Exercise> findAll() {
        return DataFactory.exercises;
    }

    @Override
    public List<Exercise> findByCriteria(String namePart, Double minBurn, LocalDate fromDate) {
        return DataFactory.exercises.stream()
                .filter(ex -> (namePart == null || ex.getName().toLowerCase().contains(namePart.toLowerCase())))
                .filter(ex -> (minBurn == null || ex.getAverageBurn() >= minBurn))
                .filter(ex -> (fromDate == null || ex.getCreatedOn().isAfter(fromDate)))
                .collect(Collectors.toList());
    }

    @Override
    public Exercise findById(int id) {
        return DataFactory.exercises.stream()
                .filter(ex -> ex.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Exercise save(Exercise exercise) {
        exercise.setId(DataFactory.exercises.size() + 1);
        DataFactory.exercises.add(exercise);
        return exercise;
    }

    @Override
    public void deleteById(int id) {
        DataFactory.exercises.removeIf(ex -> ex.getId() == id);
    }
}
