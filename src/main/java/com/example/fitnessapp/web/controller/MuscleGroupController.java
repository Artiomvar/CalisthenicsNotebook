package com.example.fitnessapp.web.controller;

import com.example.fitnessapp.domain.MuscleGroup;
import com.example.fitnessapp.service.MuscleGroupService;
import com.example.fitnessapp.web.model.MuscleGroupForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/musclegroups")
public class MuscleGroupController {

    private final MuscleGroupService muscleGroupService;

    @Autowired
    public MuscleGroupController(MuscleGroupService muscleGroupService) {
        this.muscleGroupService = muscleGroupService;
    }

    @GetMapping
    public String showMuscleGroups(
            @RequestParam(required = false) String namePart,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate afterDate,
            @RequestParam(required = false) Double minFactor,
            Model model) {
        List<MuscleGroup> muscleGroups =
                muscleGroupService.filterMuscleGroups(namePart, afterDate, minFactor);
        model.addAttribute("musclegroups", muscleGroups);
        model.addAttribute("namePart", namePart);
        model.addAttribute("afterDate", afterDate);
        model.addAttribute("minFactor", minFactor);
        return "musclegroups";
    }

    @GetMapping("/add")
    public String addMuscleGroupForm(Model model) {
        model.addAttribute("musclegroup", new MuscleGroupForm());
        return "add-musclegroup";
    }

    @PostMapping("/add")
    public String addMuscleGroupSubmit(
            @Valid @ModelAttribute("musclegroup") MuscleGroupForm form,
            BindingResult result) {
        if (result.hasErrors()) {
            return "add-musclegroup";
        }
        muscleGroupService.addMuscleGroup(
                form.getName(),
                form.getDescription(),
                form.getEstablishedOn(),
                form.getComplexityFactor()
        );
        return "redirect:/musclegroups";
    }

    @GetMapping("/details/{id}")
    public String muscleGroupDetails(@PathVariable int id, Model model) {
        MuscleGroup mg = muscleGroupService.getMuscleGroupById(id);
        if (mg == null) {
            return "redirect:/musclegroups"; // or show 404
        }
        // mg.getExercises() should be loaded if fetch=EAGER or a transaction is open
        model.addAttribute("musclegroup", mg);
        return "musclegroup-details";
    }

    @PostMapping("/delete/{id}")
    public String deleteMuscleGroup(@PathVariable int id) {
        muscleGroupService.deleteMuscleGroupById(id);
        return "redirect:/musclegroups";
    }
}
