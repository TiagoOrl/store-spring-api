package com.asm.estore.dto.client;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
public class ClientDTO {
    private int id;
    private String firstName;
    private String secondName;
    private String countryId;
    private String email;
    private Date dob;
    private Date createdAt;
    private Date updatedAt;
}
