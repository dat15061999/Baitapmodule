package com.example.demo6.services.customer;

import com.example.demo6.models.Customer;
import com.example.demo6.services.IGeneralService;

import java.math.BigDecimal;
import java.util.List;

public interface ICustomerService extends IGeneralService<Customer,Long> {
    List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLike(String fullName, String email, String phone, String address, BigDecimal balance);

    List<Customer> findAllByFullNameLike(String fullName);
    List<Customer> findAll(long id);
    List<Customer> findAll(boolean deleted);

}
