package com.example.projectManagement.controller;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.User;
import com.example.projectManagement.service.PersonService;
import com.example.projectManagement.service.RoleService;
import com.example.projectManagement.service.UserService;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    private final PersonService personService;

    private final RoleService roleService;

    public UserController(UserService service, PersonService personService, RoleService roleService) {
        this.service = service;
        this.personService = personService;
        this.roleService = roleService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String userForm(Model model) {
        model.addAttribute("userList", service.findUserByDeletedFalse());
        model.addAttribute("user", new User());
        model.addAttribute("person", personService.findAll());
        model.addAttribute("role", roleService.findAll());
        return "user";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.POST)
    public User save(@Valid User user, Model model, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result
                            .getAllErrors()
                            .stream()
                            .map((event) -> event.getDefaultMessage())
                            .collect(Collectors.toList()).toString()
            );
        }
        return service.save(user);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public User edit(@Valid User user, BindingResult result) throws NoContentException {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result
                            .getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .toList().toString()
            );
        }
        return service.update(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public User remove(@PathVariable Long id) throws NoContentException {
        return service.logicalRemoveWithReturn(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Optional<User> findById(@PathVariable Long id) throws NoContentException {
        return service.findUserByIdAndDeletedFalse(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<User> findAll(Model model) {
        return service.findUserByDeletedFalse();
    }

    @GetMapping("/findByUsername")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Optional<User> findByUsername(@RequestParam String username) throws NoContentException {
        return service.findUserByUsernameAndDeletedFalse(username);
    }

}
