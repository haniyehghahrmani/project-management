package com.example.projectManagement.controller;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Person;
import com.example.projectManagement.model.enums.Gender;
import com.example.projectManagement.service.PersonService;
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
@RequestMapping("/person")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public String personForm(Model model) {
        model.addAttribute("personList", service.findPersonByDeletedFalse());
        model.addAttribute("person", new Person());
        model.addAttribute("genders", Gender.values());
        return "person";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Person save(@Valid Person person, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result
                            .getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .toList().toString()
            );
        }
        return service.save(person);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Person edit(@Valid Person person, BindingResult result) throws NoContentException {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result
                            .getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .toList().toString()
            );
        }
        return service.update(person);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public Person remove(@PathVariable Long id) throws NoContentException {
        return service.logicalRemoveWithReturn(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Optional<Person> findById(@PathVariable Long id) throws NoContentException {
        return service.findPersonByIdAndDeletedFalse(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Person> findAll() {
        return service.findPersonByDeletedFalse();
    }

    @GetMapping("/findByNameAndLastname")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Person> findByNameAndLastname(@RequestParam String name, @RequestParam String lastname) {
        return service.findPersonByNameAndLastnameAndDeletedFalse(name, lastname);
    }

    @GetMapping("/findByNationalID")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Optional<Person> findByNationalID(@RequestParam String nationalID) throws NoContentException {
        return service.findPersonByNationalIDAndDeletedFalse(nationalID);
    }

}
