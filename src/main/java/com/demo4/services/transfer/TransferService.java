package com.demo4.services.transfer;

import com.demo4.models.Customer;
import com.demo4.models.Transfer;
import com.demo4.services.customer.CustomerService;
import com.demo4.services.customer.ICustomerService;
import com.demo4.services.deposit.DepositService;
import com.demo4.services.deposit.IDepositService;
import com.demo4.services.withdraw.IWithdrawService;
import com.demo4.services.withdraw.WithdrawService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TransferService implements ITransferService {
    private final List<Transfer> transfers;
    private final ICustomerService customerService;

    public TransferService() {
        transfers = new ArrayList<>();
        customerService = new CustomerService();
    }

    @Override
    public List<Transfer> findAll() {
        return transfers.stream().filter(transfer -> !transfer.isDeletedTransfer()).collect(Collectors.toList());
    }

    @Override
    public void save(Transfer transfer) {
        List<Customer> customers = customerService.findAll();
        for (Customer customer : customers) {
            if (Objects.equals(transfer.getSenderCustomer().getId(), customer.getId())) {
                customer.setBalance(customer.getBalance().subtract(transfer.getTransactionAmount()));
            }
            if (Objects.equals(transfer.getRecipientCustomer().getId(), customer.getId())) {
                customer.setBalance(customer.getBalance().add(transfer.getTransactionAmount()));
                transfer.getRecipientCustomer().setFullName(customer.getFullName());
            }
        }
        transfer.setCreateDate(new Date());
        transfers.add(transfer);
    }

    @Override
    public void delete(long id) {
        for (Transfer transfer : transfers) {
            if (transfer.getId() == id){
                transfer.setDeletedTransfer(true);
                break;
            }
        }

    }
}
