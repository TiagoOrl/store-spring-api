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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name="unit_price", nullable = false)
    private Float unitPrice;

    @Column(name="image_url")
    private String imageUrl;

    @Column(nullable = false)
    private Boolean active;

    @Column(name="units_in_stock", nullable = false)
    private int unitsInStock;

    @Column(name="created_at", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name="updated_at")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "fk_category_id", nullable = false)
    private ProductCategory category;
}
