package com.example.projectManagement.controller;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Task;
import com.example.projectManagement.service.TaskService;
import com.example.projectManagement.service.UserService;
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
@RequestMapping("/task")
public class TaskController {

    private final TaskService service;

    private final UserService userService;

    public TaskController(TaskService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String taskForm(Model model) {
        model.addAttribute("taskList", service.findTaskByDeletedFalse());
        model.addAttribute("task", new Task());
        model.addAttribute("priority", com.example.projectManagement.model.entity.enums.Priority.values());
        model.addAttribute("status", com.example.projectManagement.model.entity.enums.Status.values());
        model.addAttribute("user", userService.findAll());
        return "task";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.POST)
    public Task save(@Valid Task task, Model model, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result
                            .getAllErrors()
                            .stream()
                            .map((event) -> event.getDefaultMessage())
                            .collect(Collectors.toList()).toString()
            );
        }
        return service.save(task);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Task remove(Model model, @PathVariable Long id) throws NoContentException {
        return service.logicalRemoveWithReturn(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<Task> findById(Model model, @PathVariable Long id) throws NoContentException {
        return service.findTaskByIdAndDeletedFalse(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Task> findAll(Model model) {
        return service.findTaskByDeletedFalse();
    }
}
