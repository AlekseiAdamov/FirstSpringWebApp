package ru.geekbrains.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.shop.dao.ProductRepository;
import ru.geekbrains.shop.entity.Product;

import javax.validation.Valid;
import java.util.List;
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
                           @RequestParam(required = false) Double minPrice,
                           @RequestParam(required = false) Double maxPrice) {
        String logMessage = "Product list page requested";
        List<Product> products = null;

        if (minPrice != null && maxPrice != null) {
            products = productRepository.findAllByPriceBetween(minPrice, maxPrice);
            logMessage = logMessage + String.format(" with price between %s and %s", minPrice, maxPrice);
        } else if (minPrice != null) {
            products = productRepository.findAllByPriceGreaterThan(minPrice);
            logMessage = logMessage + String.format(" with price greater than %s", minPrice);
        } else if (maxPrice != null) {
            products = productRepository.findAllByPriceLessThan(maxPrice);
            logMessage = logMessage + String.format(" with price less than %s", maxPrice);
        } else {
            products = productRepository.findAll();
        }

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
