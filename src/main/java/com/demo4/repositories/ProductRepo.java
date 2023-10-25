package com.demo4.repositories;

import com.demo4.models.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class ProductRepo {
    private List<Product> findALl() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();

// Create a session
        Session session = sessionFactory.openSession();

// Define and execute an HQL query to select all products
        String hql = "FROM Product";
        Query<Product> query = session.createQuery(hql, Product.class);
        List<Product> products = query.list();

// Close the session and session factory
        session.close();
        sessionFactory.close();

// Process the list of products
        for (Product product : products) {
            System.out.println("Product Name: " + product.getProductName());
            // Add additional processing as needed
        }
        return products;
    }
}
