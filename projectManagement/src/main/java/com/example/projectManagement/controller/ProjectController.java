package com.example.projectManagement.controller;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Project;
import com.example.projectManagement.model.entity.enums.Status;
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
    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String projectForm(Model model) {
        model.addAttribute("projectList", service.findProjectByDeletedFalse());
        model.addAttribute("project", new Project());
        model.addAttribute("statusList", Arrays.stream(Status.values()).toList().stream().map(Status::getPersian));
        return "project";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Project save(@Valid Project project, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result
                            .getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .toList().toString()
            );
        }
        return service.save(project);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Project edit(@Valid Project project, BindingResult result) throws NoContentException {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result
                            .getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .toList().toString()
            );
        }
        return service.update(project);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Project remove( @PathVariable Long id) throws NoContentException {
        return service.logicalRemoveWithReturn(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<Project> findById(@PathVariable Long id) throws NoContentException {
        return service.findProjectByIdAndDeletedFalse(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Project> findAll() {
        return service.findProjectByDeletedFalse();
    }
}