package ru.geekbrains.shop.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.shop.controller.NotFoundException;
import ru.geekbrains.shop.dto.CustomerDTO;
import ru.geekbrains.shop.entity.Customer;
import ru.geekbrains.shop.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerResource {

    private final CustomerService service;

    @Autowired
    public CustomerResource(CustomerService service) {
        this.service = service;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<Customer> findAll() {
        return service.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Customer findById(@PathVariable Long id) {
        return service.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id %d not found", id)));
    }

    @PostMapping(produces = "application/json")
    public Customer create(@RequestBody CustomerDTO customer) {
        if (customer.getId() != null) {
            throw new BadRequestException("Product id should be null!");
        }
        Customer persistentCustomer = new Customer(customer);
        service.save(persistentCustomer);
        return persistentCustomer;
    }

    @PutMapping(produces = "application/json")
    public void update(@RequestBody CustomerDTO customer) {
        if (customer.getId() == null) {
            throw new BadRequestException("Product id should not be null!");
        }
        Customer persistentCustomer = new Customer(customer);
        service.save(persistentCustomer);
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
