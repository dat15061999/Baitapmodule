package com.example.demo6.services.transfer;

import com.example.demo6.models.Customer;
import com.example.demo6.models.Transfer;
import com.example.demo6.repositories.CustomerRepository;
import com.example.demo6.repositories.TransferRepository;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransferService implements ITransferService {
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }

    @Override
    public Optional<Transfer> findById(Long id) {
        return Optional.empty();
    }


    @Override
    public Transfer save(Transfer transfer) {
        List<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {
            if (Objects.equals(transfer.getSenderCustomer().getId(), customer.getId())) {
                customer.setBalance(customer.getBalance().subtract(transfer.getTransactionAmount()));
                customerRepository.save(customer);
            }
            if (Objects.equals(transfer.getRecipientCustomer().getId(), customer.getId())) {
                customer.setBalance(customer.getBalance().add(transfer.getTransactionAmount()));
                transfer.getRecipientCustomer().setFullName(customer.getFullName());
                customerRepository.save(customer);
            }
        }
        transfer.setCreateDate(new Date());
        return transferRepository.save(transfer);
    }

    @Override
    public void delete(Transfer transfer) {
        transferRepository.delete(transfer);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Transfer> transfer = transferRepository.findById(id);
        transfer.get().setDeletedTransfer(true);
        transferRepository.save(transfer.get());
    }

    @Override
    public List<Transfer> findAll(boolean deleted) {
        return transferRepository.findAll()
                .stream()
                .filter(transfer -> !transfer.isDeletedTransfer())
                .collect(Collectors.toList());
    }
}
