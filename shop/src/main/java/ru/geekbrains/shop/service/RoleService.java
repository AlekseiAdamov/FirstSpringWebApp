package ru.geekbrains.shop.service;

import ru.geekbrains.shop.dto.RoleDTO;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<RoleDTO> findAll();

    Optional<RoleDTO> findById(Long id);

    RoleDTO getById(Long id);

    void deleteById(Long id);

    void save(RoleDTO role);

    Optional<RoleDTO> findByName(String name);
}
