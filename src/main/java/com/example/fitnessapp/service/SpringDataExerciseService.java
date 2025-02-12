package com.example.fitnessapp.service;

import com.example.fitnessapp.datarepository.ExerciseDataRepository;
import com.example.fitnessapp.domain.Exercise;
import com.example.fitnessapp.exception.MyCustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Profile("datajpa") // or whichever profile you like
public class SpringDataExerciseService implements ExerciseService {

    private static final Logger logger = LoggerFactory.getLogger(SpringDataExerciseService.class);

    private final ExerciseDataRepository exerciseDataRepository;

    @Autowired
    public SpringDataExerciseService(ExerciseDataRepository exerciseDataRepository) {
        this.exerciseDataRepository = exerciseDataRepository;
    }

    @Override
    public List<Exercise> getAllExercises() {
        logger.info("Fetching all Exercises via SpringDataExerciseService");
        return exerciseDataRepository.findAll();
    }

    @Override
    public List<Exercise> filterExercises(String namePart, Double minBurn, LocalDate fromDate) {
        try {
            logger.info("Filtering Exercises with namePart={}, minBurn={}, fromDate={}",
                    namePart, minBurn, fromDate);
            // Example usage of custom queries
            if (namePart != null && !namePart.isEmpty()) {
                return exerciseDataRepository.findByNameContainingIgnoreCase(namePart);
            } else if (minBurn != null) {
                return exerciseDataRepository.findByAverageBurnGreaterThanEqual(minBurn);
            } else if (fromDate != null) {
                return exerciseDataRepository.findExercisesCreatedAfterDate(fromDate);
            } else {
                return exerciseDataRepository.findAll();
            }
        } catch (DataAccessException dae) {
            logger.error("Database error during filterExercises", dae);
            // This will be caught by our ControllerAdvice if we rethrow it or don’t handle it
            throw dae;
        }
    }

    @Override
    public Exercise addExercise(String name, Double averageBurn, LocalDate createdOn, String imageFileName) {
        logger.info("Adding new Exercise: {}, burn={}, createdOn={}", name, averageBurn, createdOn);
        Exercise ex = new Exercise();
        ex.setName(name);
        ex.setAverageBurn(averageBurn);
        ex.setCreatedOn(createdOn);
        ex.setImageFileName(imageFileName);
        return exerciseDataRepository.save(ex);
    }

    @Override
    public Exercise getExerciseById(int id) {
        logger.info("Getting Exercise by ID: {}", id);
        return exerciseDataRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteExerciseById(int id) {
        logger.info("Deleting Exercise ID: {}", id);
        // Here's where we demonstrate throwing our custom exception.
        // If the exercise doesn't exist, throw MyCustomException
        if (!exerciseDataRepository.existsById(id)) {
            throw new MyCustomException("Exercise with ID " + id + " does not exist!");
        }

        // Otherwise proceed with normal delete
        exerciseDataRepository.deleteById(id);
        logger.info("Exercise ID: {} deleted successfully.", id);
    }
}
