package com.example.fitnessapp.presentation.view;

import java.time.LocalDate;

public interface View {
    String getUserInput(String prompt);
    void showMessage(String message);
    void showMenu();
    void showAllMuscleGroups(Object data);
    void showFilteredMuscleGroups(Object data);
    void showAllExercises(Object data);
    void showFilteredExercises(Object data);
    LocalDate getDateInput(String prompt);
    Double getDoubleInput(String prompt);
}
