package com.example.fitnessapp.datarepository;

import com.example.fitnessapp.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExerciseDataRepository extends JpaRepository<Exercise, Integer> {

    // Derived Queries (no @Query needed)

    // 1) Find exercises whose name contains (case-insensitive)
    List<Exercise> findByNameContainingIgnoreCase(String namePart);

    // 2) Find exercises with averageBurn >= given value
    List<Exercise> findByAverageBurnGreaterThanEqual(Double minBurn);


    // Custom Query (with @Query)

    // 3) Use JPQL to fetch exercises created after a certain date
    @Query("SELECT e FROM Exercise e WHERE e.createdOn > :date")
    List<Exercise> findExercisesCreatedAfterDate(@Param("date") LocalDate date);

}
