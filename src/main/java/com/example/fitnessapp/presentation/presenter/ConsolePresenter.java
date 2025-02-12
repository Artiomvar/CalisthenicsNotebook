package com.example.fitnessapp.presentation.presenter;

import com.example.fitnessapp.service.ExerciseService;
import com.example.fitnessapp.service.MuscleGroupService;
import com.example.fitnessapp.presentation.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ConsolePresenter implements Presenter {

    private final View view;
    private final MuscleGroupService muscleGroupService;
    private final ExerciseService exerciseService;

    @Autowired
    public ConsolePresenter(View view, MuscleGroupService muscleGroupService, ExerciseService exerciseService) {
        this.view = view;
        this.muscleGroupService = muscleGroupService;
        this.exerciseService = exerciseService;
    }

    @Override
    public void start() {
        boolean running = true;
        while (running) {
            view.showMenu();
            String choice = view.getUserInput("Choice (0-4): ");
            switch (choice) {
                case "0":
                    running = false;
                    break;
                case "1":
                    view.showAllMuscleGroups(muscleGroupService.getAllMuscleGroups());
                    break;
                case "2":
                    LocalDate date = view.getDateInput("Enter a date (yyyy-MM-dd): ");
                    if (date != null) {
                        view.showFilteredMuscleGroups(muscleGroupService.filterMuscleGroupsAfterDate(date));
                    } else {
                        view.showMessage("No date provided. Returning to menu.");
                    }
                    break;
                case "3":
                    view.showAllExercises(exerciseService.getAllExercises());
                    break;
                case "4":
                    String namePart = view.getUserInput("Enter part of the exercise name or leave blank: ");
                    if (namePart.isEmpty()) namePart = null;
                    Double minBurn = view.getDoubleInput("Enter a minimum average burn (double) or leave blank: ");
                    LocalDate fromDate = view.getDateInput("Enter a date (yyyy-MM-dd) for createdOn after this date or leave blank: ");
                    view.showFilteredExercises(exerciseService.filterExercises(namePart, minBurn, fromDate));
                    break;
                default:
                    view.showMessage("Invalid choice. Try again.");
            }
        }
        view.showMessage("Application closed.");
    }
}
