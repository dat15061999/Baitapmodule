package com.example.demo6.services.customer;

import com.example.demo6.models.Customer;
import com.example.demo6.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findAll(long id) {
        return customerRepository.findAll()
                .stream()
                .filter(customer -> customer.getId() != id)
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> findAll(boolean deleted) {
        return customerRepository.findAll()
                .stream()
                .filter(customer -> customer.isDeleted() == deleted)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Customer customer) {
        customer.setDeleted(false);
        customerRepository.save(customer);
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }


    @Override
    public List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLike(String fullName, String email, String phone, String address, BigDecimal balance) {
        return null;
    }

    @Override
    public List<Customer> findAllByFullNameLike(String fullName) {
        return null;
    }
}
