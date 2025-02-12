package com.example.fitnessapp.repository;

import com.example.fitnessapp.domain.Exercise;
import com.example.fitnessapp.domain.MuscleGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("jdbc")
public class JdbcMuscleGroupRepository implements MuscleGroupRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMuscleGroupRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<MuscleGroup> muscleGroupMapper = (rs, rowNum) -> new MuscleGroup(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getDate("established_on").toLocalDate(),
            rs.getDouble("complexity_factor")
    );

    @Override
    public List<MuscleGroup> findAll() {
        List<MuscleGroup> groups = jdbcTemplate.query("SELECT id, name, description, established_on, complexity_factor FROM musclegroup", muscleGroupMapper);
        for (MuscleGroup mg : groups) {
            loadExercisesForMuscleGroup(mg);
        }
        return groups;
    }

    @Override
    public List<MuscleGroup> filter(String namePart, LocalDate afterDate, Double minFactor) {
        StringBuilder sql = new StringBuilder("SELECT id, name, description, established_on, complexity_factor FROM musclegroup WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (namePart != null && !namePart.isEmpty()) {
            sql.append(" AND LOWER(name) LIKE '%' || LOWER(?) || '%'");
            params.add(namePart);
        }
        if (afterDate != null) {
            sql.append(" AND established_on > ?");
            params.add(java.sql.Date.valueOf(afterDate));
        }
        if (minFactor != null) {
            sql.append(" AND complexity_factor >= ?");
            params.add(minFactor);
        }

        List<MuscleGroup> mgList = jdbcTemplate.query(sql.toString(), params.toArray(), muscleGroupMapper);
        for (MuscleGroup mg : mgList) {
            loadExercisesForMuscleGroup(mg);
        }
        return mgList;
    }

    @Override
    public MuscleGroup findById(int id) {
        MuscleGroup mg = jdbcTemplate.queryForObject(
                "SELECT id, name, description, established_on, complexity_factor FROM musclegroup WHERE id=?",
                muscleGroupMapper, id
        );
        loadExercisesForMuscleGroup(mg);
        return mg;
    }

    @Override
    public MuscleGroup save(MuscleGroup mg) {
        if (mg.getId() == 0) {
            int newId = jdbcTemplate.queryForObject("SELECT COALESCE(MAX(id),0)+1 FROM musclegroup", Integer.class);
            mg.setId(newId);
            jdbcTemplate.update("INSERT INTO musclegroup (id, name, description, established_on, complexity_factor) VALUES (?,?,?,?,?)",
                    mg.getId(), mg.getName(), mg.getDescription(), java.sql.Date.valueOf(mg.getEstablishedOn()), mg.getComplexityFactor());
        } else {
            jdbcTemplate.update("UPDATE musclegroup SET name=?, description=?, established_on=?, complexity_factor=? WHERE id=?",
                    mg.getName(), mg.getDescription(), java.sql.Date.valueOf(mg.getEstablishedOn()), mg.getComplexityFactor(), mg.getId());
        }
        return mg;
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM exercise_musclegroup WHERE musclegroup_id=?", id);
        jdbcTemplate.update("DELETE FROM musclegroup WHERE id=?", id);
    }

    private void loadExercisesForMuscleGroup(MuscleGroup mg) {
        List<Exercise> exList = jdbcTemplate.query(
                "SELECT e.id, e.name, e.average_burn, e.created_on, e.image_file_name " +
                        "FROM exercise e INNER JOIN exercise_musclegroup em ON e.id=em.exercise_id " +
                        "WHERE em.musclegroup_id=?",
                (rs, rowNum) -> {
                    Exercise ex = new Exercise(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("average_burn"),
                            rs.getDate("created_on").toLocalDate()
                    );
                    ex.setImageFileName(rs.getString("image_file_name"));
                    return ex;
                },
                mg.getId()
        );

        for (Exercise ex : exList) {
            mg.addExercise(ex);
        }
    }
}
