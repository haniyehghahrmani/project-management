package com.example.projectManagement.controller;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Permission;
import com.example.projectManagement.service.PermissionService;
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
@RequestMapping("/permission")
public class PermissionController {
    private final PermissionService service;

    public PermissionController(PermissionService service) {
        this.service = service;
    }

    @GetMapping
    public String permissionForm(Model model) {
        model.addAttribute("permissionList", service.findAll());
        model.addAttribute("permission", new Permission());
        return "permission";
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Permission save(@Valid Permission permission, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result
                            .getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .toList().toString()
            );
        }
        return service.save(permission);
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Permission edit(@Valid Permission permission, BindingResult result) throws NoContentException {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result
                            .getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .toList().toString()
            );
        }
        return service.update(permission);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Permission remove(@PathVariable Long id) {
        return service.delete(id);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<Permission> findById(@PathVariable Long id) throws NoContentException {
        return service.findById(id);
    }

    @GetMapping("/all")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Permission> findAll() {
        return service.findAll();
    }
}
