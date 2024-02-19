package com.asm.estore.entity;

import com.asm.estore.utils.DateHelper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "client")
@Data @NoArgsConstructor // auto generate getter and setters
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Column
    private String fullname;

    @Column(nullable = false)
    private String password;

    @Column(name = "country_id", nullable = false, unique = true)
    private String countryId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(name="created_at", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name="updated_at")
    private Date updatedAt;

    @OneToOne(mappedBy = "client")
    private Address address;

    @OneToMany(mappedBy = "client")
    private Set<Order> orders;

    public void setDob(String date) {
        this.dob = DateHelper.convertToLocalDate(date);
    }
}
