package com.asm.estore.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

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
    @Column(name="created_at")
    @CreationTimestamp
    private Date createdAt;
    @Column(name="updated_at")
    @UpdateTimestamp
    private Date updatedAt;
    @Column(name = "fk_client_id")
    private int fkClientId;
}
