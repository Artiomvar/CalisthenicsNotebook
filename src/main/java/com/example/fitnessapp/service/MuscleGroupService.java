package com.example.fitnessapp.service;

import com.example.fitnessapp.domain.MuscleGroup;
import java.time.LocalDate;
import java.util.List;

public interface MuscleGroupService {
    List<MuscleGroup> getAllMuscleGroups();
    List<MuscleGroup> filterMuscleGroups(String namePart, LocalDate afterDate, Double minFactor);
    MuscleGroup addMuscleGroup(String name, String description, LocalDate establishedOn, double complexityFactor);

    List<MuscleGroup> filterMuscleGroupsAfterDate(LocalDate date);

    MuscleGroup addMuscleGroup(String name, String description, LocalDate establishedOn, Double complexityFactor);

    MuscleGroup getMuscleGroupById(int id);

    void deleteMuscleGroupById(int id); // NEW
}
