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
public class JdbcExerciseRepository implements ExerciseRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcExerciseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Exercise> exerciseMapper = (rs, rowNum) -> {
        Exercise ex = new Exercise(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("average_burn"),
                rs.getDate("created_on").toLocalDate()
        );
        ex.setImageFileName(rs.getString("image_file_name"));
        return ex;
    };

    @Override
    public List<Exercise> findAll() {
        List<Exercise> exercises = jdbcTemplate.query("SELECT id, name, average_burn, created_on, image_file_name FROM exercise", exerciseMapper);
        for (Exercise ex : exercises) {
            loadMuscleGroupsForExercise(ex);
        }
        return exercises;
    }

    @Override
    public List<Exercise> findByCriteria(String namePart, Double minBurn, LocalDate fromDate) {
        StringBuilder sql = new StringBuilder("SELECT id, name, average_burn, created_on, image_file_name FROM exercise WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (namePart != null && !namePart.isEmpty()) {
            sql.append(" AND LOWER(name) LIKE '%' || LOWER(?) || '%'");
            params.add(namePart);
        }
        if (minBurn != null) {
            sql.append(" AND average_burn >= ?");
            params.add(minBurn);
        }
        if (fromDate != null) {
            sql.append(" AND created_on > ?");
            params.add(java.sql.Date.valueOf(fromDate));
        }

        List<Exercise> exercises = jdbcTemplate.query(sql.toString(), params.toArray(), exerciseMapper);
        for (Exercise ex : exercises) {
            loadMuscleGroupsForExercise(ex);
        }
        return exercises;
    }

    @Override
    public Exercise findById(int id) {
        Exercise ex = jdbcTemplate.queryForObject(
                "SELECT id, name, average_burn, created_on, image_file_name FROM exercise WHERE id = ?",
                exerciseMapper, id
        );
        loadMuscleGroupsForExercise(ex);
        return ex;
    }

    @Override
    public Exercise save(Exercise exercise) {
        if (exercise.getId() == 0) {
            int newId = jdbcTemplate.queryForObject("SELECT COALESCE(MAX(id),0)+1 FROM exercise", Integer.class);
            exercise.setId(newId);
            jdbcTemplate.update("INSERT INTO exercise (id, name, average_burn, created_on, image_file_name) VALUES (?,?,?,?,?)",
                    exercise.getId(), exercise.getName(), exercise.getAverageBurn(), java.sql.Date.valueOf(exercise.getCreatedOn()), exercise.getImageFileName());
        } else {
            jdbcTemplate.update("UPDATE exercise SET name=?, average_burn=?, created_on=?, image_file_name=? WHERE id=?",
                    exercise.getName(), exercise.getAverageBurn(), java.sql.Date.valueOf(exercise.getCreatedOn()), exercise.getImageFileName(), exercise.getId());
        }
        return exercise;
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM exercise_musclegroup WHERE exercise_id=?", id);
        jdbcTemplate.update("DELETE FROM exercise WHERE id=?", id);
    }

    private void loadMuscleGroupsForExercise(Exercise ex) {
        List<MuscleGroup> mgList = jdbcTemplate.query(
                "SELECT mg.id, mg.name, mg.description, mg.established_on, mg.complexity_factor " +
                        "FROM musclegroup mg INNER JOIN exercise_musclegroup em ON mg.id=em.musclegroup_id " +
                        "WHERE em.exercise_id=?",
                (rs, rowNum) -> new MuscleGroup(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDate("established_on").toLocalDate(),
                        rs.getDouble("complexity_factor")
                ),
                ex.getId()
        );

        for (MuscleGroup mg : mgList) {
            ex.addMuscleGroup(mg);
        }
    }
}
