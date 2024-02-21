package com.asm.estore.entity;

import com.asm.estore.utils.DateHelper;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "client")
@NoArgsConstructor
@Getter
@Setter
public class Client implements UserDetails {
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

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private Boolean deleted;

    @OneToOne(mappedBy = "client")
    private Address address;

    @OneToMany(mappedBy = "client")
    private Set<Order> orders;

    public void setDob(String date) {
        this.dob = DateHelper.convertToLocalDate(date);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role.equals("admin"))
            return List.of(
                    new SimpleGrantedAuthority("admin"),
                    new SimpleGrantedAuthority("user")
            );

        else
            return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !deleted;
    }
}
