package ru.geekbrains.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.shop.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
