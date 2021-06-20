package ru.geekbrains.webapp.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component("productCatalog")
public class ProductCatalog {
    private static final HashMap<Long, Product> CATALOG = new HashMap<>();
    private static ProductCatalog instance;

    private ProductCatalog() {
        double[] prices = {2.50, 3.50, 10.25, 1.75, 1.25,
                1.50, 5.50, 1.20, 5.45, 2.15};
        String[] names = {"Apples", "Oranges", "Beef", "Cabbage", "Potatoes",
                "Carrot", "Strawberry", "Watermelons", "Tofu", "Radish"};
        for (int i = 0; i < names.length; i++) {
            add(new Product(i + 1, names[i], prices[i]));
        }
    }

    public static ProductCatalog getInstance() {
        if (instance == null) {
            instance = new ProductCatalog();
        }
        return instance;
    }

    public Product getProduct(long id) {
        return CATALOG.get(id);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(CATALOG.values());
    }

    @Autowired
    public void add(Product product) {
        CATALOG.put(product.getId(), product);
    }

    public int getSize() {
        return CATALOG.size();
    }
}
