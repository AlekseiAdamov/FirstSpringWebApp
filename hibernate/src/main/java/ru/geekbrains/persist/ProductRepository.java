package ru.geekbrains.persist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ProductRepository {

    private final EntityManagerFactory emf;

    public ProductRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Optional<Product> getProduct(long id) {
        EntityManager em = emf.createEntityManager();
        Product product = em.find(Product.class, id);
        em.close();
        return Optional.ofNullable(product);
    }

    public List<Product> getProducts() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("select p from Product p", Product.class).getResultList();
        } catch (Exception e) {
            em.close();
            return new ArrayList<>();
        }
    }

    private void insert(Product product) {
        executeInTransaction(em -> em.persist(product));
    }

    private void update(Product product) {
        executeInTransaction(em -> em.merge(product));
    }

    public void insertOrUpdate(Product product) {
        if (product.getId() != null) {
            update(product);
        } else {
            insert(product);
        }
    }

    public void remove(long id) {
        if (getProduct(id).isEmpty()) {
            return;
        }
        executeInTransaction(em -> em.createQuery("delete from Product p where p.id = :id")
                .setParameter("id", id)
                .executeUpdate());
    }

    public long getSize() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("select count(p) from Product p", Long.class).getSingleResult();
        } catch (Exception e) {
            em.close();
            return 0;
        }
    }

    private void executeInTransaction(Consumer<EntityManager> consumer) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            consumer.accept(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
