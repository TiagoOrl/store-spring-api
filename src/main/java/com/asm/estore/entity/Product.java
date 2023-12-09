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
@Table(name="product")
@Data @NoArgsConstructor // auto generate getter and setters
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column(name="unit_price")
    private BigDecimal unitPrice;

    @Column(name="image_url")
    private String imageUrl;

    @Column
    private boolean active;

    @Column(name="units_in_stock")
    private int unitsInStock;

    @Column(name="created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    @Column(name = "fk_category_id")
    private Integer categoryId;

    @ManyToMany(mappedBy = "products")
    private Set<Order> orders;

    public Product(
        String name,
        String description,
        BigDecimal unitPrice,
        Integer unitsInStock,
        Boolean active,
        Integer categoryId
    ) {
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
        this.active = active;
        this.categoryId = categoryId;
    }
}
