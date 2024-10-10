package com.example.projectManagement.controller;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Log;
import com.example.projectManagement.service.LogService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/log")
public class LogController {
    private final LogService service;

    public LogController(LogService service) {
        this.service = service;
    }

    @GetMapping
    public String logForm(Model model) {
        model.addAttribute("logList", service.findAll());
        model.addAttribute("log", new Log());
        return "log";
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Log save(@Valid Log log, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result
                            .getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .toList().toString()
            );
        }
        return service.save(log);
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Log edit(@Valid Log log, BindingResult result) throws NoContentException {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result
                            .getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .toList().toString()
            );
        }
        return service.update(log);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Log remove(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<Log> findById(@PathVariable Long id) throws NoContentException {
        return service.findById(id);
    }

    @GetMapping("/all")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Log> findAll() {
        return service.findAll();
    }
}
