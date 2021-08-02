package ru.geekbrains.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.shop.dao.RoleRepository;
import ru.geekbrains.shop.dto.RoleDTO;
import ru.geekbrains.shop.entity.Role;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Autowired
    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RoleDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(role -> new RoleDTO(role.getId(), role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoleDTO> findById(Long id) {
        return repository.findById(id).map(role -> new RoleDTO(role.getId(), role.getName()));
    }

    @Override
    public RoleDTO getById(Long id) {
        Role role = repository.getById(id);
        return new RoleDTO(role.getId(), role.getName());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void save(RoleDTO role) {
        Role persistentRole = new Role(role.getId(), role.getName());
        repository.save(persistentRole);
    }

    @Override
    public Optional<RoleDTO> findByName(String name) {
        return repository.findByName(name).map(role -> new RoleDTO(role.getId(), role.getName()));
    }
}
