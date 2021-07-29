package ru.geekbrains.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekbrains.shop.dao.UserRepository;
import ru.geekbrains.shop.dto.UserDTO;
import ru.geekbrains.shop.dto.UserListParamsDTO;
import ru.geekbrains.shop.entity.User;
import ru.geekbrains.shop.entity.UserSpecification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        return repository.findById(id).map(user -> new UserDTO(user.getId(), user.getUsername()));
    }

    @Override
    public Page<UserDTO> findWithFilter(UserListParamsDTO params) {
        Specification<User> specification = Specification.where(null);
        if (params.getUserName() != null && !params.getUserName().isBlank()) {
            specification = specification.and(UserSpecification.userName(params.getUserName()));
        }

        final Sort sortDirection = Optional.ofNullable(params.getSortOrder())
                .orElse("asc")
                .equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(Optional.ofNullable(params.getSortBy()).orElse("id")).ascending()
                : Sort.by(Optional.ofNullable(params.getSortBy()).orElse("id")).descending();

        final PageRequest pageRequest = PageRequest.of(
                Optional.ofNullable(params.getPage()).orElse(1) - 1,
                Optional.ofNullable(params.getSize()).orElse(3),
                sortDirection
        );

        return repository.findAll(specification, pageRequest).map(user -> new UserDTO(user.getId(), user.getUsername()));
    }

    @Override
    public UserDTO getById(Long id) {
        User user = repository.getById(id);
        return new UserDTO(user.getId(), user.getUsername());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void save(UserDTO user) {
        User persistentUser = new User(user.getId(), user.getUsername(), passwordEncoder.encode(user.getPassword()));
        repository.save(persistentUser);
    }
}
