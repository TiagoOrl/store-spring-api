package com.asm.estore.dto.client;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateClientDTO {
    @Size(min = 2, max = 130, message = "invalid firstName")
    private String firstName;

    @Size(min = 2, max = 130, message = "invalid secondName")
    private String secondName;

    @Size(min = 2, max = 130, message = "invalid countryId")
    private String countryId;

    @Email(message = "invalid email")
    private String email;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "invalid date of birth (dob)")
    private String dob;

    public void setDob(String dob) {
        this.dob = dob.trim();
    }
}
