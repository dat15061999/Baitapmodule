package com.example.demo6.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "deleted")
    private boolean deletedTransfer;
    @ManyToOne
    @JoinColumn(name = "sender_customer_id")
    private Customer senderCustomer;

    @Column
    private int fees;
    @Column(name = "fees_amount")
    private BigDecimal feesAmount;
    @Column(name = "transfer_amount")
    private BigDecimal transferAmount;
    @Column(name = "transaction_amount")
    private BigDecimal transactionAmount;
    @ManyToOne
    @JoinColumn(name = "recipient_customer_id")
    private Customer recipientCustomer;


    public String getCreateDate() {
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        return outputFormat.format(createDate);
    }
}
