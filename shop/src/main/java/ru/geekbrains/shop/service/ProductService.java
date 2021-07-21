package ru.geekbrains.shop.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.shop.dto.ProductListParamsDTO;
import ru.geekbrains.shop.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Page<Product> findWithFilter(ProductListParamsDTO params);

    Optional<Product> findById(Long id);

    Product getById(Long id);

    void deleteById(Long id);

    void save(Product product);
}
