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
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.github.mfathi91.time.PersianDate;

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

    @GetMapping
    public String taskForm(Model model) {
        model.addAttribute("taskList", service.findTaskByDeletedFalse());
        model.addAttribute("task", new Task());
        model.addAttribute("priority", Priority.values());
        model.addAttribute("status", Status.values());
        model.addAttribute("user", userService.findAll());
        return "task";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
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

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Task update(@Valid Task task, BindingResult result) throws NoContentException {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result
                            .getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .toList().toString()
            );
        }
        return service.update(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public Task remove(@PathVariable Long id) throws NoContentException {
        return service.logicalRemoveWithReturn(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Optional<Task> findById(@PathVariable Long id) throws NoContentException {
        return service.findTaskByIdAndDeletedFalse(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Task> findAll() {
        return service.findTaskByDeletedFalse();
    }

    @GetMapping("/findByAssignedTo")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<Task>> findByAssignedTo(@RequestParam List<Long> userId) throws NoContentException{
        List<User> users=userService.findAllById(userId);
        if (users.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        List<Task> tasks=service.findTaskByAssignedToAndDeletedFalse(users);
        if (tasks.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/findByCreateDate")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Task> findByCreateDate(@RequestParam String faCreateDate) throws NoContentException{
        LocalDateTime gregorianCreateDate=PersianDate.parse(faCreateDate).toGregorian().atStartOfDay();
        return service.findTaskByCreateDateAndDeletedFalse(gregorianCreateDate);
    }

    @GetMapping("/findByDueDate")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Task> findByDueDate(@RequestParam String faDueDate) throws NoContentException{
        LocalDateTime gregorianDueDate=PersianDate.parse(faDueDate).toGregorian().atStartOfDay();
        return service.findTaskByDueDateAndDeletedFalse(gregorianDueDate);
    }

    @GetMapping("/findByPriority")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Task> findByPriority(@RequestParam String priority) throws NoContentException{
        return service.findTaskByPriorityAndDeletedFalse(priority);
    }

    @GetMapping("/findByStatus")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Task> findByStatus(@RequestParam String status) throws NoContentException{
        return service.findTaskByStatusAndDeletedFalse(status);
    }
}
