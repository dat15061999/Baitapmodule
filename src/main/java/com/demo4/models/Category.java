package com.demo4.models;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
@Table(name = "categories")
public class    Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
}
