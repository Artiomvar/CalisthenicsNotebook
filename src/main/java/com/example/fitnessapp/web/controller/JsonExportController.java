package com.example.fitnessapp.web.controller;

import com.example.fitnessapp.domain.Exercise;
import com.example.fitnessapp.domain.MuscleGroup;
import com.example.fitnessapp.service.ExerciseService;
import com.example.fitnessapp.service.MuscleGroupService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

@Controller
public class JsonExportController {

    private final ExerciseService exerciseService;
    private final MuscleGroupService muscleGroupService;
    private final Gson gson;

    public JsonExportController(ExerciseService exerciseService,
                                MuscleGroupService muscleGroupService) {
        this.exerciseService = exerciseService;
        this.muscleGroupService = muscleGroupService;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @GetMapping("/export/exercises")
    public ResponseEntity<InputStreamResource> exportExercises() {
        List<Exercise> exercises = exerciseService.getAllExercises();
        String json = gson.toJson(exercises);

        InputStream is = new ByteArrayInputStream(json.getBytes());
        var resource = new InputStreamResource(is);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"exercises.json\"")
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);
    }

    @GetMapping("/export/musclegroups")
    public ResponseEntity<InputStreamResource> exportMuscleGroups() {
        List<MuscleGroup> muscleGroups = muscleGroupService.getAllMuscleGroups();
        String json = gson.toJson(muscleGroups);

        InputStream is = new ByteArrayInputStream(json.getBytes());
        var resource = new InputStreamResource(is);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"musclegroups.json\"")
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);
    }
}
