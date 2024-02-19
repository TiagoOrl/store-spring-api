package com.asm.estore.dto.client;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateClientDTO {
    private Long id;

    @NotBlank(message = "first name is required")
    @Size(min = 2, max = 80)
    private String firstName;

    @NotBlank(message = "second name is required")
    @Size(min = 1, max = 230)
    private String secondName;

    @NotBlank(message = "country id is required")
    @Size(min = 5, max = 30)
    private String countryId;

    @NotBlank(message = "email is required")
    @Email
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 4, max = 22)
    private String password;

    @NotBlank(message = "role is required")
    @Pattern(regexp = "(client)|(admin)", message = "role is invalid")
    private String role;

    @NotNull(message = "dob is null")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    private String dob;

    public void setDob(String dob) {
        this.dob = dob.trim();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName.trim();
    }

}
