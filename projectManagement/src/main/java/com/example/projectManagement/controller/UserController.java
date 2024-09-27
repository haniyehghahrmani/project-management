package com.example.projectManagement.controller;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Person;
import com.example.projectManagement.model.entity.User;
import com.example.projectManagement.service.PersonService;
import com.example.projectManagement.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.EventListener;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    private final PersonService personService;

    public UserController(UserService service, PersonService personService) {
        this.service = service;
        this.personService = personService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String userForm(Model model){
        model.addAttribute("userList",service.findUserByDeletedFalse());
        model.addAttribute("user",new User());
        List<Person> person=personService.findPersonByDeletedFalse();
        return "user";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.POST)
    public User save(@Valid User user, Model model, BindingResult result){
        if (result.hasErrors()){
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

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public User edit(@Valid User user,Model model,BindingResult result) throws NoContentException {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result
                            .getAllErrors()
                            .stream()
                            .map((event) -> event.getDefaultMessage())
                            .collect(Collectors.toList()).toString()
            );
        }
        return service.update(user);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public User remove(Model model,@PathVariable Long id)throws NoContentException{
        return service.logicalRemoveWithReturn(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Optional<User> findById(Model model,@PathVariable Long id) throws NoContentException {
        return service.findUserByIdAndDeletedFalse(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<User> findAll(Model model){
        return service.findUserByDeletedFalse();
    }
}
