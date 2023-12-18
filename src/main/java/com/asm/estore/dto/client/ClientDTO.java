package com.asm.estore.dto.client;

import com.asm.estore.dto.order.OrderDTO;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;


import java.util.Date;
import java.util.List;

@Data
public class ClientDTO {
    private int id;

    @NotBlank(message = "first name is required")
    private String firstName;

    @NotBlank(message = "second name is required")
    private String secondName;

    @NotBlank(message = "country id is required")
    @Size(min = 2, max = 2)
    private String countryId;

    @NotBlank(message = "email is required")
    @Email
    private String email;

    @NotNull(message = "dob is null")
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$")
    private String dob;

    public void setDob(String dob) {
        this.dob = dob.trim();
    }

    private Date createdAt;
    private Date updatedAt;
    private List<OrderDTO> orders;
}
