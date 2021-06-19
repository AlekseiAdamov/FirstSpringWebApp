package ru.geekbrains.webapp.servlets;

import java.util.HashMap;

public class Catalog {
    private static final HashMap<Long, Product> CATALOG = new HashMap<>();
    private static Catalog instance;

    private Catalog() {
        add(new Product(1, "Apples", 2.50));
        add(new Product(2, "Oranges", 3.50));
        add(new Product(3, "Beef", 10.25));
        add(new Product(4, "Cabbage", 1.75));
        add(new Product(5, "Potatoes", 1.25));
        add(new Product(6, "Carrot", 1.50));
        add(new Product(7, "Strawberry", 5.50));
        add(new Product(8, "Watermelons", 1.20));
        add(new Product(9, "Tofu", 5.45));
        add(new Product(10, "Radish", 2.15));
    }

    public static Catalog getInstance() {
        if (instance == null) {
            instance = new Catalog();
        }
        return instance;
    }

    public Product getProduct(long id) {
        return CATALOG.get(id);
    }

    public void add(Product product) {
        CATALOG.put(product.getId(), product);
    }

    public int getSize() {
        return CATALOG.size();
    }
}
