package com.asm.estore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "product_category")
@Data
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String name;
    @Column(name="created_at")
    private Date createdAt;
    @Column(name="updated_at")
    private Date updatedAt;
}
