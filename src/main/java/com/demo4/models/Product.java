package com.demo4.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String productName;
    @Column
    private String description;
    @Column
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;

}
