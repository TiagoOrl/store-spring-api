package com.asm.estore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

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
    private Date createdAt;
    @Column(name="updated_at")
    private Date updatedAt;
}
