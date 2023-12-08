package com.asm.estore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_product")
@Data
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private int amount;
    @Column(name = "fk_order_id")
    private int fkOrderId;
    @Column(name = "fk_product_id")
    private int fkProductId;
}
