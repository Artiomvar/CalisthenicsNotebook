package com.example.fitnessapp.web.controller;

import com.example.fitnessapp.domain.Exercise;
import com.example.fitnessapp.service.ExerciseService;
import com.example.fitnessapp.web.model.ExerciseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public String showExercises(@RequestParam(required = false) String nameFilter, Model model) {
        List<Exercise> exercises = (nameFilter == null || nameFilter.isEmpty())
                ? exerciseService.getAllExercises()
                : exerciseService.filterExercises(nameFilter, null, null);
        model.addAttribute("exercises", exercises);
        model.addAttribute("nameFilter", nameFilter);
        return "exercises";
    }

    @GetMapping("/add")
    public String addExerciseForm(Model model) {
        model.addAttribute("exercise", new ExerciseForm());
        return "add-exercise";
    }

    @PostMapping("/add")
    public String addExerciseSubmit(@Valid @ModelAttribute("exercise") ExerciseForm exercise, BindingResult result) {
        if (result.hasErrors()) {
            return "add-exercise";
        }
        exerciseService.addExercise(exercise.getName(), exercise.getAverageBurn(), exercise.getCreatedOn(), exercise.getImageFileName());
        return "redirect:/exercises";
    }

    @GetMapping("/details/{id}")
    public String exerciseDetails(@PathVariable int id, Model model) {
        Exercise ex = exerciseService.getExerciseById(id);
        if (ex == null) {
            return "redirect:/exercises"; // or show a "not found" page
        }
        model.addAttribute("exercise", ex);
        return "exercise-details"; // Must match exercise-details.html exactly
    }

    @PostMapping("/delete/{id}")
    public String deleteExercise(@PathVariable int id) {
        exerciseService.deleteExerciseById(id);
        return "redirect:/exercises";
    }
}
