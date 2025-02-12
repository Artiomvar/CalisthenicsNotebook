package com.example.fitnessapp.repository;

import com.example.fitnessapp.domain.Level;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Profile("jpa")
@Transactional
public class JpaLevelRepository implements LevelRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Level> findAll() {
        return em.createQuery("SELECT l FROM Level l", Level.class).getResultList();
    }
}
