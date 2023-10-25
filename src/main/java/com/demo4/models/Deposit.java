package com.demo4.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Deposit {
    private long id;
    private Date createBy;
    private Customer customer;
    private BigDecimal depositAmount;
}
