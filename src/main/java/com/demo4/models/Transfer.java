package com.demo4.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transfer {
    private long id;
    private Date createDate;
    private boolean deletedTransfer;
    private Customer senderCustomer;
    private int fees;
    private BigDecimal feesAmount;
    private BigDecimal transferAmount;
    private BigDecimal transactionAmount;
    private Customer recipientCustomer;


    public String getCreateDate() {
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        return outputFormat.format(createDate);
    }
}
