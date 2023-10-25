package com.demo4.controller;


import com.demo4.models.Customer;
import com.demo4.models.Deposit;
import com.demo4.models.Transfer;
import com.demo4.models.Withdraw;
import com.demo4.services.customer.CustomerService;
import com.demo4.services.customer.ICustomerService;
import com.demo4.services.deposit.DepositService;
import com.demo4.services.deposit.IDepositService;
import com.demo4.services.transfer.ITransferService;
import com.demo4.services.transfer.TransferService;
import com.demo4.services.withdraw.IWithdrawService;
import com.demo4.services.withdraw.WithdrawService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/banking")
public class CustomerController {
    private final ICustomerService customerService;
    private final IDepositService depositService;
    private final IWithdrawService withdrawService;
    private final ITransferService transferService;

    public CustomerController() {
        customerService = new CustomerService();
        depositService = new DepositService();
        withdrawService = new WithdrawService();
        transferService = new TransferService();
    }

    @GetMapping("")
    public String showListPage(Model model) {
        List<Customer> customerList = customerService.findAll();
        model.addAttribute("customers", customerList);
        return "bank/home";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("check", true);
        return "bank/create";
    }

    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable long id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("check", false);
        model.addAttribute("customer", customer);
        return "bank/create";
    }

    @PostMapping("/edit/{id}")
    public String updatedCustomer(@ModelAttribute Customer customer, Model model) {
        model.addAttribute("check", false);
        if (customer.getFullName().trim().length() == 0) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Updated customer unsuccessful");
        } else {
            customerService.update(customer);
            model.addAttribute("customer", new Customer());
            model.addAttribute("success", true);
            model.addAttribute("message", "Updated customer successfully");
        }
        return "bank/create";
    }

    @PostMapping("/create")
    public String createCustomer(@ModelAttribute Customer customer, Model model) {
        model.addAttribute("check", true);
        if (customer.getFullName().trim().length() == 0) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Created customer unsuccessful");
        } else {
            customerService.save(customer);
            model.addAttribute("customer", new Customer());
            model.addAttribute("success", true);
            model.addAttribute("message", "Created customer successfully");
        }
        return "bank/create";
    }

    @GetMapping("/transfer/{id}")
    public String viewTransferMoney(@PathVariable long id, Model model) {
        Customer customer = customerService.findById(id);
        Transfer transfer = new Transfer();
        transfer.setSenderCustomer(customer);
        transfer.setFees(10);
        List<Customer> customerList = customerService.findAll(id);
        model.addAttribute("transfer", transfer);
        model.addAttribute("customers", customerList);
        return "bank/transfer";
    }

    @PostMapping("/transfer/{id}")
    public String transferMoney(@PathVariable long id, Transfer transfer, Model model) {
        Customer customer = customerService.findById(id);
        transfer.setSenderCustomer(customer);
        List<Customer> customerList = customerService.findAll(id);
        model.addAttribute("customers", customerList);
        if (transfer.getRecipientCustomer().getId() == null) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Please, Choose customer!");
        }  else if (transfer.getTransferAmount().compareTo(transfer.getSenderCustomer().getBalance()) > 0) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Balance money does not enough!");
        }
        else if (transfer.getTransferAmount().equals(BigDecimal.ZERO) || transfer.getTransferAmount().compareTo(BigDecimal.ZERO) < 0) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Transfer money unsuccessful!");
        } else {
            transferService.save(transfer);
            Transfer t = new Transfer();
            t.setSenderCustomer(customer);
            t.setFees(10);
            model.addAttribute("transfer", t);
            model.addAttribute("success", true);
            model.addAttribute("message", "Transfer money successfully!");
        }
        return "bank/transfer";
    }

    @GetMapping("/deposit/{id}")
    public String viewDeposit(@PathVariable long id, Model model) {
        Customer customer = customerService.findById(id);
        Deposit deposit = new Deposit();
        deposit.setCustomer(customer);
        model.addAttribute("deposit", deposit);
        return "bank/deposit";
    }

    @PostMapping("/deposit/{id}")
    public String depositCustomer(@ModelAttribute Deposit depositCustomer, Model model, @PathVariable long id) {
        if (depositCustomer.getDepositAmount().equals(BigDecimal.ZERO) || depositCustomer.getDepositAmount().compareTo(BigDecimal.ZERO) < 0) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Deposited customer unsuccessful");
        } else {
            Customer customer = customerService.findById(id);
            depositCustomer.setCustomer(customer);
            depositService.save(depositCustomer);
            Deposit deposit = new Deposit();
            deposit.setCustomer(customer);
            model.addAttribute("deposit", deposit);
            model.addAttribute("success", true);
            model.addAttribute("message", "Deposited customer successfully!");
        }
        return "bank/deposit";
    }

    @GetMapping("/withdraw/{id}")
    public String viewWithdraw(@PathVariable long id, Model model) {
        Customer customer = customerService.findById(id);
        Withdraw withdraw = new Withdraw();
        withdraw.setCustomer(customer);
        model.addAttribute("withdraw", withdraw);
        return "bank/withdraw";
    }

    @PostMapping("/withdraw/{id}")
    public String withdrawCustomer(@ModelAttribute Withdraw withdraw, Model model, @PathVariable long id) {
        if (withdraw.getWithdrawAmount().equals(BigDecimal.ZERO) || withdraw.getWithdrawAmount().compareTo(BigDecimal.ZERO) < 0) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Withdraw customer unsuccessful");
        } else {
            Customer customer = customerService.findById(id);
            withdraw.setCustomer(customer);
            withdrawService.save(withdraw);
            Withdraw w = new Withdraw();
            w.setCustomer(customer);
            model.addAttribute("deposit", w);
            model.addAttribute("success", true);
            model.addAttribute("message", "Withdraw customer successfully!");
        }
        return "bank/withdraw";
    }

    @GetMapping("/history")
    public String viewTransferHistory(Model model){
        List<Transfer> transfers = transferService.findAll();
        model.addAttribute("transfers",transfers);
        return "bank/history";
    }
    @GetMapping("/delete/{id}")
    public String deleteTransfer(@PathVariable long id, Model model){
        transferService.delete(id);
        model.addAttribute("success", true);
        model.addAttribute("message", "Deleted transfer unsuccessful");
        return "bank/history";
    }

}
