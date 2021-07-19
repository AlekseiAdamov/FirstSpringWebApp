package ru.geekbrains.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.geekbrains.shop.entity.Product;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {
    private Long id;
    private String name;
    private List<Product> products;
}
