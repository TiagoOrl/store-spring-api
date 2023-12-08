package com.asm.estore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_main")
@Data
public class OrderMain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column(name = "total_sum")
    private int totalSum;
    @Column(name = "fk_client_id")
    private int fkClientId;
}
