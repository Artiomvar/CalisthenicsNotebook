package com.example.fitnessapp.repository;

import com.example.fitnessapp.domain.DataFactory;
import com.example.fitnessapp.domain.MuscleGroup;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Profile("inmemory")
public class InMemoryMuscleGroupRepository implements MuscleGroupRepository {

    @Override
    public List<MuscleGroup> findAll() {
        return DataFactory.muscleGroups;
    }

    @Override
    public List<MuscleGroup> filter(String namePart, LocalDate afterDate, Double minFactor) {
        return DataFactory.muscleGroups.stream()
                .filter(mg -> (namePart == null || mg.getName().toLowerCase().contains(namePart.toLowerCase())))
                .filter(mg -> (afterDate == null || mg.getEstablishedOn().isAfter(afterDate)))
                .filter(mg -> (minFactor == null || mg.getComplexityFactor() >= minFactor))
                .collect(Collectors.toList());
    }

    @Override
    public MuscleGroup findById(int id) {
        return DataFactory.muscleGroups.stream()
                .filter(mg -> mg.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public MuscleGroup save(MuscleGroup mg) {
        mg.setId(DataFactory.muscleGroups.size() + 1);
        DataFactory.muscleGroups.add(mg);
        return mg;
    }

    @Override
    public void deleteById(int id) {
        DataFactory.muscleGroups.removeIf(mg -> mg.getId() == id);
    }


}
