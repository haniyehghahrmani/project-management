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

    private final PhaseService service;

    private final ProjectService projectService;

    public PhaseController(PhaseService service, ProjectService projectService) {
        this.service = service;
        this.projectService = projectService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String phaseForm(Model model) {
        model.addAttribute("phaseList", service.findPhaseByDeletedFalse());
        model.addAttribute("phase", new Phase());
        model.addAttribute("project", projectService.findAll());
        return "phase";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.POST)
    public Phase save(@Valid Phase phase, Model model, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result
                            .getAllErrors()
                            .stream()
                            .map((event) -> event.getDefaultMessage())
                            .collect(Collectors.toList()).toString()
            );
        }
        return service.save(phase);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Phase remove(Model model, @PathVariable Long id) throws NoContentException {
        return service.logicalRemoveWithReturn(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<Phase> findById(Model model, @PathVariable Long id) throws NoContentException {
        return service.findPhaseByIdAndDeletedFalse(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Phase> findAll(Model model) {
        return service.findPhaseByDeletedFalse();
    }
}
