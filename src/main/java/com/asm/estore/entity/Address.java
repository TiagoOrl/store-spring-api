package com.asm.estore.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "address")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String street;
    @Column
    private String neighborhood;
    @Column
    private int number;
    @Column
    private String city;
    @Column(name = "state_or_county")
    private String stateOrCounty;
    @Column
    private String country;
    @Column(name="created_at")
    @CreationTimestamp
    private Date createdAt;
    @Column(name="updated_at")
    @UpdateTimestamp
    private Date updatedAt;
    @Column(name = "fk_client_id")
    private int fkClientId;

}
