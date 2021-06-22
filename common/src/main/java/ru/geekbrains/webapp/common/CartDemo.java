package ru.geekbrains.webapp.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.webapp.config.AppConfig;

public class CartDemo {

    public static void main(String[] args) {
        final ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        final Cart cart1 = context.getBean(Cart.class);
        cart1.add(1);
        cart1.add(3);
        cart1.display();

        System.out.println("----------");

        final Cart cart2 = context.getBean(Cart.class);
        cart2.add(2);
        cart2.add(5);
        cart2.add(7);
        cart2.add(8);
        cart2.remove(7);
        cart2.display();

        System.out.println("----------");

        Cart cart3 = context.getBean(Cart.class);
        ProductCatalog catalog = context.getBean(ProductCatalog.class);
        catalog.getProducts().forEach(e -> cart3.add(e.getId()));
        cart3.display();
    }
}
