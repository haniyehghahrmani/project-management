package com.example.projectManagement.controller;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Phase;
import com.example.projectManagement.service.PhaseService;
import com.example.projectManagement.service.ProjectService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/phase")
public class PhaseController {

    private final PhaseService phaseService;
    private final ProjectService projectService;

    public PhaseController(PhaseService phaseService, ProjectService projectService) {
        this.phaseService = phaseService;
        this.projectService = projectService;
    }

    @GetMapping
    public String phaseForm(Model model) {
        model.addAttribute("phaseList", phaseService.findPhaseByDeletedFalse());
        model.addAttribute("phase", new Phase());
        model.addAttribute("project", projectService.findAll());
        return "phase";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    public Phase save(@Valid @ModelAttribute Phase phase, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessages = result.getAllErrors()
                    .stream()
                    .map(event -> event.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new ValidationException(errorMessages);
        }
        return phaseService.save(phase);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{id}")
    public Phase remove(@PathVariable Long id) throws NoContentException {
        return phaseService.logicalRemoveWithReturn(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Optional<Phase> findById(@PathVariable Long id) throws NoContentException {
        return phaseService.findPhaseByIdAndDeletedFalse(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<Phase> findAll() {
        return phaseService.findPhaseByDeletedFalse();
    }
}
