package ru.geekbrains.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.geekbrains.shop.entity.Customer;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private List<Customer> customers;
}
