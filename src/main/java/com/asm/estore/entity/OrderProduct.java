package com.asm.estore.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_product")
@Data @NoArgsConstructor
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Integer amount;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "unit_price")
    private Float unitPrice;

    @Column(name = "fk_order_id")
    private Long fkOrderId;

    @Column(name = "fk_product_id")
    private Long fkProductId;

    public OrderProduct(Integer amount, Long fkOrderId, Long fkProductId) {
        this.amount = amount;
        this.fkOrderId = fkOrderId;
        this.fkProductId = fkProductId;
    }
}
