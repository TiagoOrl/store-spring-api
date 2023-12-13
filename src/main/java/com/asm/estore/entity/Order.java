package com.asm.estore.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "order_main")
@Data @NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "total_sum")
    private Float totalSum;

    @Column(name="created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    private Boolean finalized;

    @Column(name = "fk_client_id")
    private Long clientId;


    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "fk_order_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_product_id")
    )
    private Set<Product> products;

    public Order(Long clientId, Float initialSum) {
        this.clientId = clientId;
        this.totalSum = initialSum;
        this.finalized = false;
    }
}
