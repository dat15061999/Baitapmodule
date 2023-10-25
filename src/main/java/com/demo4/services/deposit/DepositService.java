package com.demo4.services.deposit;

import com.demo4.models.Customer;
import com.demo4.models.Deposit;
import com.demo4.services.customer.CustomerService;
import com.demo4.services.customer.ICustomerService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DepositService implements IDepositService{
    private final List<Deposit> deposits;
    private final ICustomerService customerService;
    public DepositService(){
        customerService = new CustomerService();
        deposits = new ArrayList<>();
    }

    @Override
    public List<Deposit> findAll() {
        return deposits;
    }

    @Override
    public void save(Deposit deposit) {
        List<Customer> customers = customerService.findAll();
        for (Customer customer : customers){
            if (Objects.equals(customer.getId(), deposit.getCustomer().getId())){
                customer.setBalance(customer.getBalance().add(deposit.getDepositAmount()));
                break;
            }
        }
        deposit.setCreateBy(new Date());
        deposits.add(deposit);
    }

    @Override
    public void delete(long id) {

    }
}
