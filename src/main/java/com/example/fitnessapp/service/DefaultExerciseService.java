package com.example.fitnessapp.service;

import com.example.fitnessapp.domain.Exercise;
import com.example.fitnessapp.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DefaultExerciseService implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public DefaultExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    @Override
    public List<Exercise> filterExercises(String namePart, Double minBurn, LocalDate fromDate) {
        return exerciseRepository.findByCriteria(namePart, minBurn, fromDate);
    }

    @Override
    public Exercise addExercise(String name, Double averageBurn, LocalDate createdOn, String imageFileName) {
        Exercise ex = new Exercise(0, name, averageBurn, createdOn);
        ex.setImageFileName(imageFileName);
        return exerciseRepository.save(ex);
    }

    @Override
    public Exercise getExerciseById(int id) {
        return exerciseRepository.findById(id);
    }

    @Override
    public void deleteExerciseById(int id) {
        exerciseRepository.deleteById(id);
    }
}
