package com.example.fitnessapp.repository;

import com.example.fitnessapp.domain.DataFactory;
import com.example.fitnessapp.domain.Level;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryLevelRepository implements LevelRepository {

    @Override
    public List<Level> findAll() {
        return DataFactory.levels;
    }
}
