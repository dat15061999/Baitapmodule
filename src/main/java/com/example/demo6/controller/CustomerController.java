package com.example.demo6.controller;

import com.example.demo6.models.Customer;
import com.example.demo6.models.Deposit;
import com.example.demo6.models.Transfer;
import com.example.demo6.models.Withdraw;
import com.example.demo6.services.customer.ICustomerService;
import com.example.demo6.services.deposit.IDepositService;
import com.example.demo6.services.transfer.ITransferService;
import com.example.demo6.services.withdraw.IWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/banking")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IDepositService depositService;
    @Autowired
    private IWithdrawService withdrawService;
    @Autowired
    private ITransferService transferService;

    @GetMapping
    public ModelAndView showListPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("bank/home");
        List<Customer> customers = customerService.findAll(true);
        modelAndView.addObject("customers", customers);
        modelAndView.addObject("success", true);
        return modelAndView;
    }

    @GetMapping("/create")
    public String viewCreate(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("check", true);
        return "bank/create";
    }

    @PostMapping("/create")
    public String createCustomer(Customer customer, Model model) {
        customer.setBalance(BigDecimal.ZERO);
        customer.setDeleted(true);
        customerService.save(customer);
        model.addAttribute("success", true);
        model.addAttribute("message", "Created customer successfully!");
        return "bank/create";
    }

    @GetMapping("/edit/{id}")
    public String viewEdit(@PathVariable long id, Model model) {
        Optional<Customer> customer = customerService.findById(id);
        model.addAttribute("customer", customer.get());
        model.addAttribute("check", false);
        return "bank/create";
    }

    @PostMapping("/edit/{id}")
    public String editCustomer(@PathVariable long id, Customer c, Model model) {
        customerService.deleteById(id);
        c.setId(id);
        customerService.save(c);
        model.addAttribute("success", true);
        model.addAttribute("message", "Updated customer successfully!");
        return "bank/create";
    }

    @GetMapping("/deposit/{id}")
    public String viewDeposit(@PathVariable long id, Model model) {
        Optional<Customer> customer = customerService.findById(id);
        Deposit deposit = new Deposit();
        deposit.setCustomer(customer.get());
        model.addAttribute("deposit", deposit);
        return "bank/deposit";
    }

    @PostMapping("/deposit/{id}")
    public String depositCustomer(@ModelAttribute Deposit depositCustomer, Model model, @PathVariable long id) {
        Optional<Customer> c = customerService.findById(id);
        Customer customer = c.get();
        Deposit deposit = new Deposit();
        if (depositCustomer.getDepositAmount() == null){
            depositCustomer.setDepositAmount(BigDecimal.ZERO);
        }
        if (depositCustomer.getDepositAmount().equals(BigDecimal.ZERO) || depositCustomer.getDepositAmount().compareTo(BigDecimal.ZERO) < 0) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Deposited customer unsuccessful");
        } else {
            customer.setBalance(customer.getBalance().add(depositCustomer.getDepositAmount()));
            customerService.save(customer);
            depositCustomer.setCustomer(customer);
            depositCustomer.setCreateBy(new Date());
            depositService.save(depositCustomer);
            model.addAttribute("success", true);
            model.addAttribute("message", "Deposited customer successfully!");
        }
        deposit.setCustomer(customer);
        model.addAttribute("deposit", deposit);
        return "bank/deposit";
    }

    @GetMapping("/withdraw/{id}")
    public String viewWithdraw(@PathVariable long id, Model model) {
        Optional<Customer> customer = customerService.findById(id);
        Withdraw withdraw = new Withdraw();
        withdraw.setCustomer(customer.get());
        model.addAttribute("withdraw", withdraw);
        return "bank/withdraw";
    }

    @PostMapping("/withdraw/{id}")
    public String depositWithdraw(@ModelAttribute Withdraw withdraw, Model model, @PathVariable long id) {
        Optional<Customer> c = customerService.findById(id);
        Customer customer = c.get();
        Withdraw w = new Withdraw();
        if (withdraw.getWithdrawAmount() == null) {
            withdraw.setWithdrawAmount(BigDecimal.ZERO);
        }
        if (withdraw.getWithdrawAmount().equals(BigDecimal.ZERO) || withdraw.getWithdrawAmount().compareTo(BigDecimal.ZERO) < 0) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Withdraw customer unsuccessful");
        } else {
            customer.setBalance(customer.getBalance().subtract(withdraw.getWithdrawAmount()));
            customerService.save(customer);
            withdraw.setCustomer(customer);
            withdraw.setCreateBy(new Date());
            withdrawService.save(withdraw);
            model.addAttribute("success", true);
            model.addAttribute("message", "Withdraw customer successfully!");
        }
        w.setCustomer(customer);
        model.addAttribute("withdraw", w);
        return "bank/withdraw";
    }

    @GetMapping("/transfer/{id}")
    public String viewTransferMoney(@PathVariable long id, Model model) {
        Optional<Customer> customer = customerService.findById(id);
        Transfer transfer = new Transfer();
        transfer.setSenderCustomer(customer.get());
        transfer.setFees(10);
        List<Customer> customerList = customerService.findAll(id);
        model.addAttribute("transfer", transfer);
        model.addAttribute("customers", customerList);
        return "bank/transfer";
    }

    @PostMapping("/transfer/{id}")
    public String transferMoney(@PathVariable long id, Transfer transfer, Model model) {
        Optional<Customer> customer = customerService.findById(id);
        transfer.setSenderCustomer(customer.get());
        List<Customer> customerList = customerService.findAll(id);
        model.addAttribute("customers", customerList);

        if (transfer.getRecipientCustomer().getId() == null) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Please, Choose customer!");
        } else if (transfer.getTransferAmount().compareTo(transfer.getSenderCustomer().getBalance()) > 0) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Balance money does not enough!");
        } else if (transfer.getTransferAmount().equals(BigDecimal.ZERO) || transfer.getTransferAmount().compareTo(BigDecimal.ZERO) < 0) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Transfer money unsuccessful!");
        } else {
            transferService.save(transfer);
            Transfer t = new Transfer();
            t.setSenderCustomer(customer.get());
            t.setFees(10);
            model.addAttribute("transfer", t);
            model.addAttribute("success", true);
            model.addAttribute("message", "Transfer money successfully!");
        }
        return "bank/transfer";
    }

    @GetMapping("/history")
    public String viewTransferHistory(Model model){
        List<Transfer> transfers = transferService.findAll(false);
        model.addAttribute("transfers",transfers);
        return "bank/history";
    }
    @GetMapping("/deleteTransfer/{id}")
    public String deleteTransfer(@PathVariable long id, Model model){
        transferService.deleteById(id);
        model.addAttribute("success", true);
        model.addAttribute("message", "Deleted transfer unsuccessful");
        return "bank/history";
    }
    @GetMapping("/delete/{id}")
    public String viewDelete(@PathVariable long id, Model model){
        Optional<Customer> customer = customerService.findById(id);
        model.addAttribute("customer", customer.get());
        return "bank/delete";
    }
    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable long id, Model model) {
        Optional<Customer> customer = customerService.findById(id);
        customerService.delete(customer.get());
        model.addAttribute("success", true);
        model.addAttribute("message", "Deleted transfer unsuccessful");
        return "redirect:/banking/";
    }
}
