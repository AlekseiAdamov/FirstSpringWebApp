package ru.geekbrains.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.springboot.persist.Product;
import ru.geekbrains.springboot.persist.ProductRepository;

import javax.validation.Valid;

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
    public String listPage(Model model) {
        log.info("Product list page requested");

        model.addAttribute("products", productRepository.getProducts());
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

        model.addAttribute("product", productRepository.getProduct(id)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id %d not found", id))));
        return "product_form";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        log.info("Deleting user with id {}", id);
        productRepository.remove(id);
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
        if (productRepository.getProduct(product.getId()).isPresent()) {
            log.info("Updating product");
            productRepository.update(product);
        } else {
            log.info("Saving new product");
            productRepository.insert(product);
        }
        return "redirect:/product";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
