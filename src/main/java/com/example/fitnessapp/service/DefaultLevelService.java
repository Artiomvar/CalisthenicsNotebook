package com.example.fitnessapp.service;

import com.example.fitnessapp.domain.Level;
import com.example.fitnessapp.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultLevelService implements LevelService {

    private final LevelRepository levelRepository;

    @Autowired
    public DefaultLevelService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Override
    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }
}
