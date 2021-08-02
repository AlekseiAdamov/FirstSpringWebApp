package ru.geekbrains.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.geekbrains.shop.entity.Customer;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin(value = "0.01")
    private Double price;

    private List<Customer> customers;
}
