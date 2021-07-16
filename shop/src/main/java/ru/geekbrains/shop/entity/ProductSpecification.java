package ru.geekbrains.shop.entity;

import org.springframework.data.jpa.domain.Specification;

public final class ProductSpecification {

    public static Specification<Product> productName(String prefix) {
        return (root, query, builder) -> builder.like(root.get("name"), prefix + "%");
    }

    public static Specification<Product> minPrice(Double price) {
        return (root, query, builder) -> builder.ge(root.get("price"), price);
    }

    public static Specification<Product> maxPrice(Double price) {
        return (root, query, builder) -> builder.le(root.get("price"), price);
    }
}
