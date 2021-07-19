package ru.geekbrains.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.shop.dto.ProductListParamsDTO;
import ru.geekbrains.shop.dao.ProductRepository;
import ru.geekbrains.shop.entity.Product;
import ru.geekbrains.shop.entity.ProductSpecification;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Product> findWithFilter(ProductListParamsDTO params) {

        Specification<Product> specification = Specification.where(null);
        if (params.getProductName() != null && !params.getProductName().isBlank()) {
            specification = specification.and(ProductSpecification.productName(params.getProductName()));
        }
        if (params.getMinPrice() != null) {
            specification = specification.and(ProductSpecification.minPrice(params.getMinPrice()));
        }
        if (params.getMaxPrice() != null) {
            specification = specification.and(ProductSpecification.maxPrice(params.getMaxPrice()));
        }

        final Sort sortDirection = Optional.ofNullable(params.getSortOrder())
                .orElse("asc")
                .equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(Optional.ofNullable(params.getSortBy()).orElse("id")).ascending()
                : Sort.by(Optional.ofNullable(params.getSortBy()).orElse("id")).descending();

        final PageRequest pageRequest = PageRequest.of(
                Optional.ofNullable(params.getPage()).orElse(1) - 1,
                Optional.ofNullable(params.getSize()).orElse(3),
                sortDirection
        );

        return repository.findAll(specification, pageRequest);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Product getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void save(Product product) {
        repository.save(product);
    }
}
