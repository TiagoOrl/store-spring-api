package com.asm.estore.entity;

import com.asm.estore.utils.DateHelper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "country_id")
    private String countryId;

    @Column
    private String email;

    @Column
    private LocalDate dob;

    @Column(name="created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name="updated_at")
    private Date updatedAt;

    @OneToOne(mappedBy = "client")
    private Address address;

    // non owning side of relationship
    //One Client to MANY Orders
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "fk_client_id")
    private Set<Order> orders;

    public void setDob(String date) {
        this.dob = DateHelper.convertToLocalDate(date);
    }
}
