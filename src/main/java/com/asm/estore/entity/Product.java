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
    private Float unitPrice;

    @Column(name="image_url")
    private String imageUrl;

    @Column
    private Boolean active;

    @Column(name="units_in_stock")
    private int unitsInStock;

    @Column(name="created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name="updated_at")
    private Date updatedAt;

    @Column(name = "fk_category_id")
    private Long categoryId;
}
