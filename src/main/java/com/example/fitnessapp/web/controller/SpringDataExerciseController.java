package com.example.fitnessapp.web.controller;

import com.example.fitnessapp.domain.Exercise;
import com.example.fitnessapp.exception.MyCustomException;
import com.example.fitnessapp.service.ExerciseService;
import com.example.fitnessapp.web.model.ExerciseForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@Profile("datajpa")
@RequestMapping("/exercises/data")
public class SpringDataExerciseController {

    private static final Logger logger = LoggerFactory.getLogger(SpringDataExerciseController.class);

    private final ExerciseService exerciseService;

    public SpringDataExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public String showExercises(
            @RequestParam(required = false) String nameFilter,
            @RequestParam(required = false) Double minBurn,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fromDate,
            Model model
    ) {
        List<Exercise> exercises = exerciseService.filterExercises(nameFilter, minBurn, fromDate);
        model.addAttribute("exercises", exercises);
        model.addAttribute("nameFilter", nameFilter);
        model.addAttribute("minBurn", minBurn);
        model.addAttribute("fromDate", fromDate);
        return "exercises-data";
    }

    @GetMapping("/add")
    public String addExerciseForm(Model model) {
        model.addAttribute("exercise", new ExerciseForm());
        return "add-exercise-data";
    }

    @PostMapping("/add")
    public String addExerciseSubmit(@Valid @ModelAttribute("exercise") ExerciseForm exerciseForm,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return "add-exercise-data";
        }
        exerciseService.addExercise(
                exerciseForm.getName(),
                exerciseForm.getAverageBurn(),
                exerciseForm.getCreatedOn(),
                exerciseForm.getImageFileName()
        );
        return "redirect:/exercises/data";
    }

    @GetMapping("/details/{id}")
    public String exerciseDetails(@PathVariable int id, Model model) {
        Exercise ex = exerciseService.getExerciseById(id);
        if (ex == null) {
            return "redirect:/exercises/data";
        }
        model.addAttribute("exercise", ex);
        return "exercise-details-data";
    }

    @PostMapping("/delete/{id}")
    public String deleteExercise(@PathVariable int id, Model model) {
        logger.info("Trying to delete Exercise ID: {}", id);
        try {
            exerciseService.deleteExerciseById(id);
        } catch (MyCustomException ce) {
            // Handle the custom exception here at the controller level
            logger.error("Caught MyCustomException for ID={}. Message={}", id, ce.getMessage());
            model.addAttribute("errorMessage", ce.getMessage());
            // Return a custom page or a general error
            return "error-other";
        }
        return "redirect:/exercises/data";
    }
}
