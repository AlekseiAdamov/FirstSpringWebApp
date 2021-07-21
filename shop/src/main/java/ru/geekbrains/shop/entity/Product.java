package ru.geekbrains.shop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.geekbrains.shop.dto.ProductDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128, nullable = false)
    private String name;

    @Column(precision = 2)
    private Double price;

    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<Customer> customers;

    public Product(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(ProductDTO product) {
        if (product.getId() != null) {
            this.id = product.getId();
        }
        this.name = product.getName();
        this.price = product.getPrice();
        if (product.getCustomers() != null) {
            this.customers = new ArrayList<>(product.getCustomers());
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
