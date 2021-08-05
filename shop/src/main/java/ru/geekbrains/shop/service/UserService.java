package ru.geekbrains.shop.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.shop.dto.UserDTO;
import ru.geekbrains.shop.dto.UserListParamsDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> findAll();

    Optional<UserDTO> findById(Long id);

    Page<UserDTO> findWithFilter(UserListParamsDTO params);

    UserDTO getById(Long id);

    void deleteById(Long id);

    void save(UserDTO user);

    Optional<UserDTO> findByName(String username);
}
