package com.demo4.services.product;


import com.demo4.models.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;




public class ProductService implements IProductService {
    private final SessionFactory sessionFactory;

    public ProductService() {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    @Override
    public List<Product> findAll() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "FROM Product";
        Query<Product> query = session.createQuery(hql, Product.class);
        List<Product> products = query.list();
        session.close();
        sessionFactory.close();
        return products;
    }

    @Override
    public Product findById(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Product.class, id);
    }

    @Override
    public void save(Product product) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(product);
    }

    @Override
    public void update(int id, Product product) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // Lấy đối tượng Product từ cơ sở dữ liệu
        Product existingProduct = session.get(Product.class, id);

        if (existingProduct != null) {
            // Cập nhật dữ liệu của đối tượng
            existingProduct.setProductName(product.getProductName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setCategory(product.getCategory());

            // Sử dụng merge() để cập nhật đối tượng
            session.merge(existingProduct);
            transaction.commit();
        }

        session.close();
        sessionFactory.close();
    }

    @Override
    public void remove(int id) {
        Session session = sessionFactory.getCurrentSession();
        Product product = session.byId(Product.class).load(id);
        session.delete(product);
    }

}
