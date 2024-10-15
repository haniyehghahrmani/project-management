package com.example.projectManagement.controller;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Project;
import com.example.projectManagement.model.enums.Status;
import com.example.projectManagement.service.ProjectService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String projectForm(Model model) {
        model.addAttribute("projectList", projectService.findProjectByDeletedFalse());
        model.addAttribute("project", new Project());
        model.addAttribute("statusList", Arrays.stream(Status.values()).map(Status::getPersian).toList());
        return "project";
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Project save(@Valid @ModelAttribute Project project, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessages = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList().toString();
            throw new ValidationException(errorMessages);
        }
        return projectService.save(project);
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Project edit(@Valid @ModelAttribute Project project, BindingResult result) throws NoContentException {
        if (result.hasErrors()) {
            String errorMessages = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList().toString();
            throw new ValidationException(errorMessages);
        }
        return projectService.update(project);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Project remove(@PathVariable Long id) throws NoContentException {
        return projectService.logicalRemoveWithReturn(id);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Optional<Project> findById(@PathVariable Long id) throws NoContentException {
        return projectService.findProjectByIdAndDeletedFalse(id);
    }

    @GetMapping("/all")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Project> findAll() {
        return projectService.findProjectByDeletedFalse();
    }
}
