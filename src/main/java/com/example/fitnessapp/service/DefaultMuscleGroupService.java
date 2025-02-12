package com.example.fitnessapp.service;

import com.example.fitnessapp.domain.MuscleGroup;
import com.example.fitnessapp.repository.MuscleGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DefaultMuscleGroupService implements MuscleGroupService {

    private final MuscleGroupRepository muscleGroupRepository;

    @Autowired
    public DefaultMuscleGroupService(MuscleGroupRepository muscleGroupRepository) {
        this.muscleGroupRepository = muscleGroupRepository;
    }

    @Override
    public List<MuscleGroup> getAllMuscleGroups() {
        return muscleGroupRepository.findAll();
    }

    @Override
    public List<MuscleGroup> filterMuscleGroups(String namePart, LocalDate afterDate, Double minFactor) {
        return muscleGroupRepository.filter(namePart, afterDate, minFactor);
    }

    @Override
    public MuscleGroup addMuscleGroup(String name, String description, LocalDate establishedOn, double complexityFactor) {
        MuscleGroup mg = new MuscleGroup(0, name, description, establishedOn, complexityFactor);
        return muscleGroupRepository.save(mg);
    }

    @Override
    public List<MuscleGroup> filterMuscleGroupsAfterDate(LocalDate date) {
        return muscleGroupRepository.filter(null, date, null);
    }

    @Override
    public MuscleGroup addMuscleGroup(String name, String description, LocalDate establishedOn, Double complexityFactor) {
        MuscleGroup mg = new MuscleGroup(0, name, description, establishedOn, complexityFactor);
        return muscleGroupRepository.save(mg);
    }

    @Override
    public MuscleGroup getMuscleGroupById(int id) {
        return muscleGroupRepository.findById(id);
    }

    @Override
    public void deleteMuscleGroupById(int id) {
        muscleGroupRepository.deleteById(id);
    }
}
