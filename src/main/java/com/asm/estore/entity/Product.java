package com.asm.estore.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="product")
@Data // auto generate getter and setters
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


    // MANY Products to One Category
    // OWNING side of relationship
    @ManyToOne
    @JoinColumn(name = "fk_category_id", nullable = false)
    private ProductCategory category;
}
