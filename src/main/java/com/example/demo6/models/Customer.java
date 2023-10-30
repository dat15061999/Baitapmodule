package com.example.demo6.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column
    private String email;
    @Column
    private String phone;
    @Column
    private String address;
    @Column
    private BigDecimal balance;
    @Column(name = "deleted")

    private boolean deleted;

    public boolean isDeleted() {
        return deleted;
    }
}
