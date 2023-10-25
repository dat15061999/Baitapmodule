package com.demo4.services.withdraw;

import com.demo4.models.Customer;
import com.demo4.models.Withdraw;
import com.demo4.services.customer.CustomerService;
import com.demo4.services.customer.ICustomerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WithdrawService implements IWithdrawService {
    private final ICustomerService customerService;
    private final List<Withdraw> withdraws;

    public WithdrawService() {
        customerService = new CustomerService();
        withdraws = new ArrayList<>();
    }

    @Override
    public List<Withdraw> findAll() {
        return withdraws;
    }

    @Override
    public void save(Withdraw withdraw) {
        List<Customer> customers = customerService.findAll();
        for (Customer customer : customers) {
            if (Objects.equals(customer.getId(), withdraw.getCustomer().getId())) {
                customer.setBalance(customer.getBalance().subtract(withdraw.getWithdrawAmount()));
                break;
            }
        }
        withdraws.add(withdraw);
    }

    @Override
    public void delete(long id) {

    }
}
