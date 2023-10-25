package com.demo4.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private BigDecimal balance;
    private Boolean deleted;
}
