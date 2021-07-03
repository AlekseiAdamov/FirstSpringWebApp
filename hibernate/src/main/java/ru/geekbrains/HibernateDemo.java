package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class HibernateDemo {

    public static void main(String[] args) {

        EntityManagerFactory emf = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        ProductRepository productRepository = new ProductRepository(emf);
        final List<Product> products = List.of(
                new Product(null, "Apples", 2.50),
                new Product(null, "Oranges", 3.50),
                new Product(null, "Beef", 10.25),
                new Product(null, "Cabbage", 1.75),
                new Product(null, "Potatoes", 1.25),
                new Product(null, "Carrot", 1.50),
                new Product(null, "Strawberry", 5.50),
                new Product(null, "Watermelons", 1.20),
                new Product(null, "Tofu", 5.45),
                new Product(null, "Radish", 2.15)
        );

        products.forEach(productRepository::insertOrUpdate);

        System.out.println("----------");
        System.out.println("Get all products:\n");
        productRepository.getProducts().forEach(System.out::println);

        System.out.println("----------");
        System.out.println("Get single existing product:\n");
        System.out.println(productRepository.getProduct(2L));

        System.out.println("----------");
        System.out.println("Get non existing product\n");
        System.out.println(productRepository.getProduct(11L));

        System.out.println("----------");
        System.out.println("Get all products after removing some of them:\n");

        productRepository.remove(3L);
        productRepository.remove(5L);
        productRepository.remove(8L);

        productRepository.getProducts().forEach(System.out::println);

        System.out.println("----------");
        System.out.println("Get all products after updating some of them:\n");

        productRepository.insertOrUpdate(new Product(1L, "Red apples", 2.85));
        productRepository.insertOrUpdate(new Product(9L, "Seitan", 9.00));
        productRepository.insertOrUpdate(new Product(11L, "Pineapples", 11.00));

        productRepository.getProducts().forEach(System.out::println);

        System.out.println("----------");
        System.out.printf("Total number of products after all operations: %d.\n", productRepository.getSize());
    }
}
