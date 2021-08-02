package ru.geekbrains.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.shop.dto.UserDTO;
import ru.geekbrains.shop.service.UserService;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {

    private static final String REGISTER_FORM_PAGE = "register_form";
    private final UserService service;

    public RegistrationController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String register(Model model) {
        model.addAttribute("user", new UserDTO());
        return REGISTER_FORM_PAGE;
    }

    @PostMapping
    public String createNewUser(@Valid @ModelAttribute("user") UserDTO user, BindingResult result) {
        checkIfRepeatedPasswordIsTheSame(user, result);
        checkIfUsernameIsUnique(user, result);
        if (result.hasErrors()) {
            return REGISTER_FORM_PAGE;
        }
        log.info("Registering new user");
        service.save(user);
        return "redirect:/login";
    }

    private void checkIfRepeatedPasswordIsTheSame(UserDTO user, BindingResult result) {
        if (user.getPassword() != null && !user.getPassword().equals(user.getRepeatedPassword())) {
            result.rejectValue("repeatedPassword", "", "Passwords must be equal!");
        }
    }

    private void checkIfUsernameIsUnique(UserDTO user, BindingResult result) {
        if (service.findByName(user.getUsername()).isPresent()) {
            String message = String.format("User name '%s' is already in use!", user.getUsername());
            result.rejectValue("username", "", message);
        }
    }
}
