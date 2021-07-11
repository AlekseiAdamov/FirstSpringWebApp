package ru.geekbrains.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.shop.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByPriceGreaterThan(double minPrice);

    List<Product> findAllByPriceLessThan(double maxPrice);

    List<Product> findAllByPriceBetween(double minPrice, double maxPrice);
}
