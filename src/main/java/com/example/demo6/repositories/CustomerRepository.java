    package com.example.demo6.repositories;
    import com.example.demo6.models.Customer;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    @Repository

    public interface CustomerRepository extends JpaRepository<Customer, Long> {

    }
