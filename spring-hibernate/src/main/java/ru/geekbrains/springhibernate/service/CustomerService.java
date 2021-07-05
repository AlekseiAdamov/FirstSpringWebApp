package ru.geekbrains.springhibernate.service;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import ru.geekbrains.springhibernate.persist.Customer;
import ru.geekbrains.springhibernate.persist.Product;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides information about customers and products.
 */
@Service
public class CustomerService {

    /**
     * Provides {@link javax.persistence.EntityManager} instance.
     */
    private final SessionFactory sf;

    public CustomerService(SessionFactory sf) {
        this.sf = sf;
    }

    /**
     * @param customerId Customer ID.
     * @return Products purchased by the customer with the specified ID.
     */
    public List<Product> getProducts(long customerId) {
        EntityManager em = sf.createEntityManager();
        Customer customer = em.find(Customer.class, customerId);
        if (customer != null) {
            return customer.getProducts();
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * @param productId Product ID.
     * @return Product with the specified ID.
     */
    public Product getProduct(long productId) {
        EntityManager em = sf.createEntityManager();
        return em.find(Product.class, productId);
    }

    /**
     * @param productId Product ID.
     * @return Customers who purchased the product with the specified ID.
     */
    public List<Customer> getCustomers(long productId) {
        EntityManager em = sf.createEntityManager();
        Product product = em.find(Product.class, productId);
        if (product != null) {
            return product.getCustomers();
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * @param customerId Customer ID.
     * @return Customer with the specified ID.
     */
    public Customer getCustomer(long customerId) {
        EntityManager em = sf.createEntityManager();
        return em.find(Customer.class, customerId);
    }
}
