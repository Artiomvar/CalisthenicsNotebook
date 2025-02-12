package com.example.fitnessapp.repository;

import com.example.fitnessapp.domain.Exercise;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Profile("jpa")
@Transactional
public class JpaExerciseRepository implements ExerciseRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Exercise> findAll() {
        return em.createQuery("SELECT e FROM Exercise e", Exercise.class).getResultList();
    }

    @Override
    public List<Exercise> findByCriteria(String namePart, Double minBurn, LocalDate fromDate) {
        StringBuilder sb = new StringBuilder("SELECT e FROM Exercise e WHERE 1=1 ");
        if (namePart != null && !namePart.isEmpty()) {
            sb.append("AND LOWER(e.name) LIKE LOWER(CONCAT('%', :namePart, '%')) ");
        }
        if (minBurn != null) {
            sb.append("AND e.averageBurn >= :minBurn ");
        }
        if (fromDate != null) {
            sb.append("AND e.createdOn > :fromDate ");
        }

        TypedQuery<Exercise> query = em.createQuery(sb.toString(), Exercise.class);
        if (namePart != null && !namePart.isEmpty()) {
            query.setParameter("namePart", namePart);
        }
        if (minBurn != null) {
            query.setParameter("minBurn", minBurn);
        }
        if (fromDate != null) {
            query.setParameter("fromDate", fromDate);
        }

        return query.getResultList();
    }

    @Override
    public Exercise findById(int id) {
        return em.find(Exercise.class, id);
    }

    @Override
    public Exercise save(Exercise exercise) {
        if (exercise.getId() == 0) {
            em.persist(exercise);
        } else {
            exercise = em.merge(exercise);
        }
        return exercise;
    }

    @Override
    public void deleteById(int id) {
        Exercise ex = em.find(Exercise.class, id);
        if (ex != null) {
            em.remove(ex);
        }
    }
}
