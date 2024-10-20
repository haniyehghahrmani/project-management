package com.example.projectManagement.controller;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Team;
import com.example.projectManagement.service.ProjectService;
import com.example.projectManagement.service.TeamService;
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
@RequestMapping("team")
public class TeamController {

    private final TeamService service;

    private final UserService userService;

    private final ProjectService projectService;

    public TeamController(TeamService service, UserService userService, ProjectService projectService) {
        this.service = service;
        this.userService = userService;
        this.projectService = projectService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String teamFrom(Model model) {
        model.addAttribute("teamList", service.findTeamByDeletedFalse());
        model.addAttribute("team", new Team());
        model.addAttribute("user", userService.findAll());
        model.addAttribute("project", projectService.findAll());
        return "team";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.POST)
    public Team save(@Valid Team team, Model model, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result
                            .getAllErrors()
                            .stream()
                            .map((event) -> event.getDefaultMessage())
                            .collect(Collectors.toList()).toString()
            );
        }
        return service.save(team);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Team remove(Model model, @PathVariable Long id) throws NoContentException {
        return service.logicalRemoveWithReturn(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<Team> findById(Model model, @PathVariable Long id) throws NoContentException {
        return service.findTeamByIdAndDeletedFalse(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Team> findAll(Model model) {
        return service.findTeamByDeletedFalse();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public List<Team> findByTeamMembers(@PathVariable String username){
        return service.findTeamByTeamMembersAndDeletedFalse(username);
    }
}
