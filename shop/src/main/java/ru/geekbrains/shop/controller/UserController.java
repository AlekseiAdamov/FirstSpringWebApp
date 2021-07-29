package ru.geekbrains.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.shop.dto.UserDTO;
import ru.geekbrains.shop.dto.UserListParamsDTO;
import ru.geekbrains.shop.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    private static final String USER_FORM_PAGE = "user_form";
    private static final String USER_LIST_PAGE = "user";
    private static final String USER_ATTRIBUTE = "user";
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String listPage(Model model, UserListParamsDTO params) {

        final String logMessage = String.format("User list page requested with parameters: userName = %s",
                params.getUserName());
        log.info(logMessage);

        final Page<UserDTO> users = service.findWithFilter(params);

        model.addAttribute("reverseSortOrder", "asc".equals(params.getSortOrder()) ? "desc" : "asc");
        model.addAttribute("users", users);
        return USER_LIST_PAGE;
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        log.info("New user page requested");

        model.addAttribute(USER_ATTRIBUTE, new UserDTO());
        return USER_FORM_PAGE;
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        log.info("Edit user page requested");

        Optional<UserDTO> user = service.findById(id);
        if (user.isPresent()) {
            model.addAttribute(USER_ATTRIBUTE, service.getById(id));
        } else {
            throw new NotFoundException(String.format("User with id %d not found", id));
        }
        return USER_FORM_PAGE;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        log.info("Deleting user with id {}", id);
        service.deleteById(id);
        return "redirect:/user";
    }

    @GetMapping("/error")
    public String error(Model model) {
        log.info("Non existing page requested");

        model.addAttribute("message", "404 Page not found");
        return "not_found";
    }

    @PostMapping
    public String update(@Valid @ModelAttribute("user") UserDTO user, BindingResult result) {
        if (user.getPassword() != null && !user.getPassword().equals(user.getRepeatedPassword())) {
            result.rejectValue("repeatedPassword", "", "Passwords must be equal!");
        }
        if (result.hasErrors()) {
            return USER_FORM_PAGE;
        }
        if (user.getId() != null && service.findById(user.getId()).isPresent()) {
            log.info("Updating user");
        } else {
            log.info("Saving new user");
        }
        service.save(user);
        return "redirect:/user";
    }

    @ExceptionHandler()
    public ModelAndView notFoundExceptionHandler(NotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
