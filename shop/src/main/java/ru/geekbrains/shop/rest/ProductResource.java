package ru.geekbrains.shop.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.shop.controller.NotFoundException;
import ru.geekbrains.shop.dto.ProductDTO;
import ru.geekbrains.shop.entity.Product;
import ru.geekbrains.shop.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductResource {

    private final ProductService service;

    @Autowired
    public ProductResource(ProductService service) {
        this.service = service;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<Product> findAll() {
        return service.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Product findById(@PathVariable Long id) {
        return service.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id %d not found", id)));
    }

    @PostMapping(produces = "application/json")
    public Product create(@RequestBody ProductDTO product) {
        if (product.getId() != null) {
            throw new BadRequestException("Product id should be null!");
        }
        Product persistentProduct = new Product(product);
        service.save(persistentProduct);
        return persistentProduct;
    }

    @PutMapping(produces = "application/json")
    public void update(@RequestBody ProductDTO product) {
        if (product.getId() == null) {
            throw new BadRequestException("Product id should not be null!");
        }
        Product persistentProduct = new Product(product);
        service.save(persistentProduct);
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
