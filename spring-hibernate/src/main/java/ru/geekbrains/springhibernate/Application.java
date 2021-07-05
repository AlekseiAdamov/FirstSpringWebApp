package ru.geekbrains.springhibernate;

import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.springhibernate.service.CustomerService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = (AnnotationConfigApplicationContext) SpringApplication.run(Application.class, args);
        CustomerService service = context.getBean(CustomerService.class,
                new Configuration().configure("hibernate.cfg.xml").buildSessionFactory()
        );

        showCustomerProducts(service, 1L);
        showCustomerProducts(service, 3L);

        showProductCustomers(service, 1L);
        showProductCustomers(service, 4L);
        showProductCustomers(service, 8L);
    }

    /**
     * Prints information about customers who bought the product with the specified ID.
     *
     * @param service Customer service that provides information about product's customers.
     * @param productId Product ID.
     */
    private static void showProductCustomers(CustomerService service, long productId) {
        System.out.println("----------");
        System.out.printf("Customers of product %s:\n", service.getProduct(productId));
        service.getCustomers(productId).forEach(System.out::println);
        System.out.println();
    }

    /**
     * Prints information about products bought by the customer with the specified ID.
     *
     * @param service Customer service that provides information about customer's products.
     * @param customerId Customer ID.
     */
    private static void showCustomerProducts(CustomerService service, long customerId) {
        System.out.println("----------");
        System.out.printf("Products of customer %s:\n", service.getCustomer(customerId));
        service.getProducts(customerId).forEach(System.out::println);
        System.out.println();
    }
}
