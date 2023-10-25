package com.demo4.services.category;

import com.demo4.models.Category;
import com.demo4.models.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CategoryService implements ICategoryService{
    private static List<Category> categories = new ArrayList<>();

    @Override
    public List<Category> findAll() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();

// Create a session
        Session session = sessionFactory.openSession();

// Define and execute an HQL query to select all products
        String hql = "FROM Category";
        Query<Category> query = session.createQuery(hql, Category.class);
        List<Category> categories = query.list();

// Close the session and session factory
        session.close();
        sessionFactory.close();

// Process the list of products
        for (Category category : categories) {
            System.out.println("Product Name: " + category.getTitle());
            // Add additional processing as needed
        }
        return categories;
    }

    @Override
    public Category findById(Long id) {
        List<Category> categories1 = findAll();
        for (Category category : categories1) {
            if (category.getId() == id) return category;
        }
        return null ;
    }

    @Override
    public Category save(Category category) {
        return null;
    }

    @Override
    public void delete(Category category) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}

