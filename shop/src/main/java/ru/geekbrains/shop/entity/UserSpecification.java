package ru.geekbrains.shop.entity;

import org.springframework.data.jpa.domain.Specification;

public final class UserSpecification {

    public static Specification<User> username(String prefix) {
        return (root, query, builder) -> builder.like(root.get("username"), "%" + prefix + "%");
    }
}
