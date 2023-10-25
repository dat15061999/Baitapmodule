package com.demo4.services.customer;

import com.demo4.models.Customer;
import com.demo4.services.IGeneralService;

import java.util.List;

public interface ICustomerService extends IGeneralService<Customer, Long> {
    void update(Customer customer);
    List<Customer> findAll(long id);
}
