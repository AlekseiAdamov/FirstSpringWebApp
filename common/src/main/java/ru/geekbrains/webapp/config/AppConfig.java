package ru.geekbrains.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.geekbrains.webapp.common.Cart;
import ru.geekbrains.webapp.common.Product;
import ru.geekbrains.webapp.common.ProductCatalog;

@Configuration
public class AppConfig {

    @Bean(name = "product")
    @Scope("prototype")
    public Product product() {
        return new Product();
    }

    @Bean(name = "productCatalog")
    public ProductCatalog productCatalog() {
        return ProductCatalog.getInstance();
    }

    @Bean(name = "cart")
    @Scope("prototype")
    public Cart cart() {
        return new Cart();
    }
}
