package com.example.projectManagement.controller;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Comment;
import com.example.projectManagement.service.CommentService;
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
@RequestMapping("/comment")
public class CommentController {

    private final CommentService service;

    private final UserService userService;

    private final TaskService taskService;

    public CommentController(CommentService service, UserService userService, TaskService taskService) {
        this.service = service;
        this.userService = userService;
        this.taskService = taskService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String commentForm(Model model){
        model.addAttribute("commentList",service.findCommentByDeletedFalse());
        model.addAttribute("comment",new Comment());
        model.addAttribute("author",userService.findUserByDeletedFalse());
        model.addAttribute("relatedTask",taskService.findTaskByDeletedFalse());
        return "comment";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.POST)
    public Comment save(@Valid Comment comment, Model model, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result
                            .getAllErrors()
                            .stream()
                            .map((event) -> event.getDefaultMessage())
                            .collect(Collectors.toList()).toString()
            );
        }
        return service.save(comment);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Comment remove(Model model, @PathVariable Long id) throws NoContentException {
        return service.logicalRemoveWithReturn(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<Comment> findById(Model model, @PathVariable Long id) throws NoContentException {
        return service.findCommentByIdAndDeletedFalse(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Comment> findAll(Model model) {
        return service.findCommentByDeletedFalse();
    }
}
