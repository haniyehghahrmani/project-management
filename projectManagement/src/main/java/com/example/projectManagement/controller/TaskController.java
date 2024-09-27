package com.example.projectManagement.controller;

import com.example.projectManagement.model.entity.Task;
import com.example.projectManagement.model.entity.User;
import com.example.projectManagement.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskService service;

//    private final UserService userService;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String taskForm(Model model){
        model.addAttribute("taskList",service.findTaskByDeletedFalse());
        model.addAttribute("task",new Task());
        model.addAttribute("priority",com.example.projectManagement.model.entity.enums.Priority.values());
        model.addAttribute("priority",com.example.projectManagement.model.entity.enums.Status.values());
//        List<User>
//        model.addAttribute("user",)
        return "task";
    }
}
