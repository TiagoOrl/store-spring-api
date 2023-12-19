package com.asm.estore.dto.client;


import lombok.Data;
import java.util.Date;

@Data
public class AllClientsDTO {
    private int id;
    private String firstName;
    private String secondName;
    private String countryId;
    private String email;
    private String dob;
    private Date createdAt;
    private Date updatedAt;
}
