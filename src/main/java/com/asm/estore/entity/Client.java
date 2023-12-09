package com.asm.estore.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "client")
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String secondName;
    @Column(name = "country_id")
    private String countryId;
    @Column
    private String email;
    @Column
    private Date dob;
    @Column(name="created_at")
    @CreationTimestamp
    private Date createdAt;
    @Column(name="updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    // non owning side of relationship
    @OneToOne(mappedBy = "client")
    private Address address;

    // non owning side of relationship
    //One Client to MANY Orders
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "fk_client_id")
    private Set<Order> orders;
}
