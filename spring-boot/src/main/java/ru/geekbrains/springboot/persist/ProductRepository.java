package ru.geekbrains.springboot.persist;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {
    private final Map<Long, Product> productRepository = new ConcurrentHashMap<>();
    private final AtomicLong identity = new AtomicLong(0);

    @PostConstruct
    public void init() {
        final double[] prices = {2.50, 3.50, 10.25, 1.75, 1.25,
                1.50, 5.50, 1.20, 5.45, 2.15};
        final String[] names = {"Apples", "Oranges", "Beef", "Cabbage", "Potatoes",
                "Carrot", "Strawberry", "Watermelons", "Tofu", "Radish"};
        for (int i = 0; i < names.length; i++) {
            this.insert(new Product(names[i], prices[i]));
        }
    }

    public Optional<Product> getProduct(long id) {
        return Optional.ofNullable(productRepository.get(id));
    }

    public List<Product> getProducts() {
        return new ArrayList<>(productRepository.values());
    }

    public void insert(Product product) {
        final long id = identity.incrementAndGet();
        product.setId(id);
        productRepository.put(id, product);
    }

    public void update(Product product) {
        productRepository.put(product.getId(), product);
    }

    public void remove(long id) {
        productRepository.remove(id);
    }

    public int getSize() {
        return productRepository.size();
    }
}
