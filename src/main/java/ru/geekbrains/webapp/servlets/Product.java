package ru.geekbrains.webapp.servlets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class Product {
    private long id;

    @Setter
    private String name;

    @Setter
    private double price;

    public Product(long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
