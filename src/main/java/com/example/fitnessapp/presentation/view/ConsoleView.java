package com.example.fitnessapp.presentation.view;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@Component
public class ConsoleView implements View {
    private final Scanner sc = new Scanner(System.in);

    @Override
    public String getUserInput(String prompt) {
        System.out.println(prompt);
        return sc.nextLine().trim();
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showMenu() {
        showMessage("\nWhat would you like to do?");
        showMessage("==========================");
        showMessage("0) Quit");
        showMessage("1) Show all muscle groups");
        showMessage("2) Show muscle groups established after a certain date");
        showMessage("3) Show all exercises");
        showMessage("4) Show exercises filtered by optional criteria (name, minBurn, fromDate)");
    }

    @Override
    public void showAllMuscleGroups(Object data) {
        showMessage("\nAll Muscle Groups");
        showMessage("=================");
        ((Iterable<?>)data).forEach(System.out::println);
    }

    @Override
    public void showFilteredMuscleGroups(Object data) {
        showMessage("\nFiltered Muscle Groups");
        showMessage("======================");
        ((Iterable<?>)data).forEach(System.out::println);
    }

    @Override
    public void showAllExercises(Object data) {
        showMessage("\nAll Exercises");
        showMessage("=============");
        ((Iterable<?>)data).forEach(System.out::println);
    }

    @Override
    public void showFilteredExercises(Object data) {
        showMessage("\nFiltered Exercises");
        showMessage("==================");
        ((Iterable<?>)data).forEach(System.out::println);
    }

    @Override
    public LocalDate getDateInput(String prompt) {
        String input = getUserInput(prompt);
        if (input.isEmpty()) return null;
        try {
            return LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            showMessage("Invalid date. Ignoring date filter.");
            return null;
        }
    }

    @Override
    public Double getDoubleInput(String prompt) {
        String input = getUserInput(prompt);
        if (input.isEmpty()) return null;
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            showMessage("Invalid number. Ignoring.");
            return null;
        }
    }
}
