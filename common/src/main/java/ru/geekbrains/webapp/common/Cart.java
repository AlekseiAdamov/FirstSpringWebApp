package ru.geekbrains.webapp.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ru.geekbrains.webapp.config.AppConfig;

import java.util.ArrayList;
import java.util.List;

@Component("cart")
public class Cart {

    private final List<Product> products;

    @Autowired
    @Qualifier("productCatalog")
    private final ProductCatalog catalog;

    public Cart() {
        products = new ArrayList<>();
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        catalog = context.getBean(ProductCatalog.class);
    }

    public void add(long productId) {
        final Product product = catalog.getProduct(productId);
        if (product != null) {
            products.add(product);
        }
    }

    public void remove(long productId) {
        final Product product = catalog.getProduct(productId);
        if (product != null) {
            products.remove(product);
        }
    }

    public void display() {
        System.out.println("Cart products:");
        products.forEach(System.out::println);
    }
}
