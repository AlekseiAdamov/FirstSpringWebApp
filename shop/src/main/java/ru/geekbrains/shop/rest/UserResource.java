package ru.geekbrains.shop.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.shop.controller.NotFoundException;
import ru.geekbrains.shop.dto.UserDTO;
import ru.geekbrains.shop.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserResource {

    private final UserService service;

    @Autowired
    public UserResource(UserService service) {
        this.service = service;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<UserDTO> findAll() {
        return service.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public UserDTO findById(@PathVariable Long id) {
        return service.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d not found", id)));
    }

    @PostMapping(produces = "application/json")
    public UserDTO create(@RequestBody UserDTO user) {
        if (user.getId() != null) {
            throw new BadRequestException("User id should be null!");
        }
        service.save(user);
        return user;
    }

    @PutMapping(produces = "application/json")
    public void update(@RequestBody UserDTO user) {
        if (user.getId() == null) {
            throw new BadRequestException("User id should not be null!");
        }
        service.save(user);
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
