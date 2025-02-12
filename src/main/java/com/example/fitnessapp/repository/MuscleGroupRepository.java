package com.example.fitnessapp.repository;

import com.example.fitnessapp.domain.MuscleGroup;
import java.time.LocalDate;
import java.util.List;

public interface MuscleGroupRepository {
    List<MuscleGroup> findAll();
    List<MuscleGroup> filter(String namePart, LocalDate afterDate, Double minFactor);
    MuscleGroup findById(int id);
    MuscleGroup save(MuscleGroup mg);
    void deleteById(int id);
}
