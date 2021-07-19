package ru.geekbrains.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.shop.entity.Product;
import ru.geekbrains.shop.dto.ProductListParamsDTO;
import ru.geekbrains.shop.dto.ProductDTO;
import ru.geekbrains.shop.service.ProductService;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {

    private static final String PRODUCT_FORM_PAGE = "product_form";
    private static final String PRODUCT_LIST_PAGE = "product";
    private static final String PRODUCT_ATTRIBUTE = "product";
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public String listPage(Model model, ProductListParamsDTO params) {

        final String logMessage = String.format("Product list page requested with parameters: " +
                        "productName = %s, " +
                        "minPrice = %s, " +
                        "maxPrice = %s",
                params.getProductName(),
                params.getMinPrice(),
                params.getMaxPrice());

        final Page<Product> products = service.findWithFilter(params);

        log.info(logMessage);
        model.addAttribute("products", products);
        return PRODUCT_LIST_PAGE;
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        log.info("New product page requested");

        model.addAttribute(PRODUCT_ATTRIBUTE, new Product());
        return PRODUCT_FORM_PAGE;
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        log.info("Edit product page requested");

        Optional<Product> product = service.findById(id);
        if (product.isPresent()) {
            model.addAttribute(PRODUCT_ATTRIBUTE, service.getById(id));
        } else {
            throw new NotFoundException(String.format("Product with id %d not found", id));
        }
        return PRODUCT_FORM_PAGE;
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        log.info("Deleting user with id {}", id);
        service.deleteById(id);
        return "redirect:/product";
    }

    @GetMapping("/error")
    public String error(Model model) {
        log.info("Non existing page requested");

        model.addAttribute("message", "404 Page not found");
        return "not_found";
    }

    @PostMapping
    public String update(@Valid ProductDTO product, BindingResult result) {
        if (result.hasErrors()) {
            return PRODUCT_FORM_PAGE;
        }
        if (product.getId() != null && service.findById(product.getId()).isPresent()) {
            log.info("Updating product");
        } else {
            log.info("Saving new product");
        }
        Product persistentProduct = new Product(product);
        service.save(persistentProduct);
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
