package ru.geekbrains.webapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.webapp.common.Product;
import ru.geekbrains.webapp.common.ProductRepository;

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
        model.addAttribute("product", productRepository.getProduct(id));
        return "product_form";
    }

    @PostMapping
    public String update(Product product) {
        if (productRepository.getProduct(product.getId()) != null) {
            log.info("Updating product");
            productRepository.update(product);
        } else {
            log.info("Saving new product");
            productRepository.insert(product);
        }
        return "redirect:/product";
    }
}
