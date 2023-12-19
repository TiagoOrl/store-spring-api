package com.asm.estore.dto.address;

import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class CreateAddressDTO {
    @NotNull(message = "clientId is required")
    @Min(value = 0, message = "clientId should be >= 0")
    private Long clientId;

    @NotBlank(message = "street is required")
    @Size(min = 2, max = 130, message = "invalid size for street")
    private String street;

    @NotBlank(message = "neighborhood is required")
    @Size(min = 2, max = 130, message = "invalid size for neighborhood")
    private String neighborhood;

    @NotNull(message = "number is required")
    @Min(1) @Max(99999)
    private Integer number;

    @NotBlank(message = "city is required")
    @Size(min = 2, max = 100, message = "invalid size for city")
    private String city;

    @NotBlank(message = "stateOrCounty is required")
    @Size(min = 2, max = 130, message = "invalid size for stateOrCounty")
    private String stateOrCounty;

    @NotBlank(message = "country is required")
    @Size(min = 2, max = 130, message = "invalid size for country")
    private String country;

}
