package com.demo4.services.customer;

import com.demo4.models.Customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomerService implements ICustomerService{
    private static final List<Customer> customers = new ArrayList<>();
    private static long id = 1L;

    static {
        customers.add(new Customer(id++, "Dat", "nva@co.cc", "2345", "28 Nguyễn Tri Phương", BigDecimal.valueOf(10000), false));
        customers.add(new Customer(id++, "Huy", "nva@co.cc", "2345", "28 Nguyễn Tri Phương", BigDecimal.valueOf(10000), false));
    }

    @Override
    public List<Customer> findAll() {
        return customers;
    }
    public List<Customer> findAll(long id) {
        return customers
                .stream()
                .filter(customer -> customer.getId() != id)
                .collect(Collectors.toList());
    }

    @Override
    public Customer findById(Long id) {
        List<Customer> customerList = findAll();
        for (Customer customer : customerList) {
            if (Objects.equals(customer.getId(), id)){
                return customer;
            }
        }
        return null;
    }

    @Override
    public Customer save(Customer customer) {
        customer.setId(id++);
        customer.setBalance(BigDecimal.ZERO);
        customers.add(customer);
        return customer;
    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public void deleteById(Long aLong) {

    }


    @Override
    public void update(Customer customer) {
        List<Customer> customerList = findAll();
        for (Customer c : customerList) {
            if (Objects.equals(c.getId(), customer.getId())){
                c.setFullName(customer.getFullName());
                c.setEmail(customer.getEmail());
                c.setAddress(customer.getAddress());
                c.setDeleted(customer.getDeleted());
                break;
            }
        }
    }
}
