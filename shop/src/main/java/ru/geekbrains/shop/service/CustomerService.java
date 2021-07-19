package ru.geekbrains.shop.service;

import ru.geekbrains.shop.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> findAll();

    Optional<Customer> findById(Long id);

    Optional<Customer> getById(Long id);

    void deleteById(Long id);

    void save(Customer customer);
}
