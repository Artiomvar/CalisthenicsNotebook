package com.example.fitnessapp.repository;

import com.example.fitnessapp.domain.Level;
import java.util.List;

public interface LevelRepository {
    List<Level> findAll();
}
