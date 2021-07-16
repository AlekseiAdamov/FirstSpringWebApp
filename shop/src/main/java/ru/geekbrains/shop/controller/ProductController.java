package ru.geekbrains.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.shop.dao.ProductRepository;
import ru.geekbrains.shop.entity.Product;
import ru.geekbrains.shop.entity.ProductSpecification;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String listPage(Model model,
                           @RequestParam(required = false) Optional<String> productName,
                           @RequestParam(required = false) Optional<Double> minPrice,
                           @RequestParam(required = false) Optional<Double> maxPrice,
                           @RequestParam(required = false) Optional<Integer> page,
                           @RequestParam(required = false) Optional<Integer> size,
                           @RequestParam(required = false) Optional<String> sortBy,
                           @RequestParam(required = false) Optional<String> sortOrder) {
        final String logMessage = String.format("Product list page requested with parameters: " +
                        "productName = %s, " +
                        "minPrice = %s, " +
                        "maxPrice = %s",
                productName.orElse(null),
                minPrice.orElse(null),
                maxPrice.orElse(null));

        Specification<Product> specification = Specification.where(null);
        if (productName.isPresent()) {
            specification = specification.and(ProductSpecification.productName(productName.get()));
        }
        if (minPrice.isPresent()) {
            specification = specification.and(ProductSpecification.minPrice(minPrice.get()));
        }
        if (maxPrice.isPresent()) {
            specification = specification.and(ProductSpecification.maxPrice(maxPrice.get()));
        }

        final Sort sortDirection = sortOrder.orElse("asc").equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy.orElse("id")).ascending() : Sort.by(sortBy.orElse("id")).descending();

        final PageRequest pageRequest = PageRequest.of(
                page.orElse(1) - 1,
                size.orElse(3),
                sortDirection
        );
        final Page<Product> products = productRepository.findAll(specification, pageRequest);

        log.info(logMessage);
        model.addAttribute("products", products);
        return "product";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        log.info("New product page requested");

        model.addAttribute("product", new Product());
        return "product_form";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        log.info("Edit product page requested");

        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            model.addAttribute("product", productRepository.getById(id));
        } else {
            throw new NotFoundException(String.format("Product with id %d not found", id));
        }
        return "product_form";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        log.info("Deleting user with id {}", id);
        productRepository.deleteById(id);
        return "redirect:/product";
    }

    @GetMapping("/error")
    public String error(Model model) {
        log.info("Non existing page requested");

        model.addAttribute("message", "404 Page not found");
        return "not_found";
    }

    @PostMapping
    public String update(@Valid Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "product_form";
        }
        if (product.getId() != null && productRepository.findById(product.getId()).isPresent()) {
            log.info("Updating product");
        } else {
            log.info("Saving new product");
        }
        productRepository.save(product);
        return "redirect:/product";
    }

    @ExceptionHandler()
    public ModelAndView notFoundExceptionHandler(NotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
