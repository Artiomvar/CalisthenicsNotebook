package com.example.fitnessapp.repository;

import com.example.fitnessapp.domain.MuscleGroup;
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
public class JpaMuscleGroupRepository implements MuscleGroupRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<MuscleGroup> findAll() {
        return em.createQuery("SELECT m FROM MuscleGroup m", MuscleGroup.class).getResultList();
    }

    @Override
    public List<MuscleGroup> filter(String namePart, LocalDate afterDate, Double minFactor) {
        StringBuilder sb = new StringBuilder("SELECT m FROM MuscleGroup m WHERE 1=1 ");
        if (namePart != null && !namePart.isEmpty()) {
            sb.append("AND LOWER(m.name) LIKE LOWER(CONCAT('%', :namePart, '%')) ");
        }
        if (afterDate != null) {
            sb.append("AND m.establishedOn > :afterDate ");
        }
        if (minFactor != null) {
            sb.append("AND m.complexityFactor >= :minFactor ");
        }

        TypedQuery<MuscleGroup> query = em.createQuery(sb.toString(), MuscleGroup.class);
        if (namePart != null && !namePart.isEmpty()) {
            query.setParameter("namePart", namePart);
        }
        if (afterDate != null) {
            query.setParameter("afterDate", afterDate);
        }
        if (minFactor != null) {
            query.setParameter("minFactor", minFactor);
        }

        return query.getResultList();
    }

    @Override
    public MuscleGroup findById(int id) {
        return em.find(MuscleGroup.class, id);
    }

    @Override
    public MuscleGroup save(MuscleGroup mg) {
        if (mg.getId() == 0) {
            em.persist(mg);
        } else {
            mg = em.merge(mg);
        }
        return mg;
    }

    @Override
    public void deleteById(int id) {
        MuscleGroup mg = em.find(MuscleGroup.class, id);
        if (mg != null) {
            em.remove(mg);
        }
    }
}
