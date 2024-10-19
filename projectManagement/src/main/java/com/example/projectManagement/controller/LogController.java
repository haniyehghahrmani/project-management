package com.example.projectManagement.controller;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Log;
import com.example.projectManagement.service.LogService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Log> save(@Valid Log log, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result
                            .getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .toList().toString()
            );
        }
        Log savedLog = service.save(log);
        return new ResponseEntity<>(savedLog, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Log> edit(@Valid Log log, BindingResult result) throws NoContentException {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result
                            .getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .toList().toString()
            );
        }
        Log updatedLog = service.update(log);
        return new ResponseEntity<>(updatedLog, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) throws NoContentException {
        Optional<Log> logOptional = service.findById(id);
        if (logOptional.isEmpty()) {
            throw new NoContentException("Log with id " + id + " not found.");
        }
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Log> findById(@PathVariable Long id) throws NoContentException {
        Optional<Log> log = service.findById(id);
        if (log.isEmpty()) {
            throw new NoContentException("Log with id " + id + " not found.");
        }
        return new ResponseEntity<>(log.get(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Log>> findAll() {
        List<Log> logs = service.findAll();
        if (logs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
}
