package com.example.projectManagement.controller;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Task;
import com.example.projectManagement.model.entity.User;
import com.example.projectManagement.model.enums.Priority;
import com.example.projectManagement.model.enums.Status;
import com.example.projectManagement.service.TaskService;
import com.example.projectManagement.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
        model.addAttribute("priority", Priority.values());
        model.addAttribute("status", Status.values());
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

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "{/username}",method = RequestMethod.GET)
    public List<Task> findByAssignedTo(@PathVariable List<User> username) throws NoContentException{
        return service.findTaskByAssignedToAndDeletedFalse(username);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "{/createDate}",method = RequestMethod.GET)
    public List<Task> findByCreateDate(@PathVariable LocalDateTime createDate) throws NoContentException{
        return service.findTaskByCreateDateAndDeletedFalse(createDate);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "{/dueDate}",method = RequestMethod.GET)
    public List<Task> findByDueDate(@PathVariable LocalDateTime dueDate) throws NoContentException{
        return service.findTaskByDueDateAndDeletedFalse(dueDate);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "{/priority}",method = RequestMethod.GET)
    public List<Task> findByPriority(@PathVariable String priority) throws NoContentException{
        return service.findTaskByPriorityAndDeletedFalse(priority);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "{/status}",method = RequestMethod.GET)
    public List<Task> findByStatus(@PathVariable String status) throws NoContentException{
        return service.findTaskByStatusAndDeletedFalse(status);
    }
}
