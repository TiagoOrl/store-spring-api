package com.asm.estore.dto.address;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateAddressDTO {

    @Size(min = 2, max = 130, message = "invalid size for street")
    private String street;

    @Size(min = 2, max = 130, message = "invalid size for neighborhood")
    private String neighborhood;

    @Min(1) @Max(99999)
    private Integer number;

    @Size(min = 2, max = 100, message = "invalid size for city")
    private String city;

    @Size(min = 2, max = 130, message = "invalid size for stateOrCounty")
    private String stateOrCounty;

    @Size(min = 2, max = 130, message = "invalid size for country")
    private String country;
}
