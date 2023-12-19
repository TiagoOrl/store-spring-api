package com.asm.estore.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductDTO {
    private Long id;

    @NotBlank(message = "name is required")
    @Size(min = 3, max = 200, message = "name is invalid")
    private String name;

    @NotBlank(message = "description is required")
    @Size(min = 4, max = 600, message = "description is invalid")
    private String description;

    @NotNull(message = "unitPrice is required")
    @Min(value = 0, message = "unitPrice should be >= 0")
    private Float unitPrice;

    @Size(min = 6, max = 350, message = "imageUrl is invalid")
    private String imageUrl;

    @NotNull(message = "unitsInStock is required")
    @Min(value = 0, message = "unitsInStock should be >= 0")
    private Integer unitsInStock;

    @NotNull(message = "categoryId is required")
    private Long categoryId;

    @NotNull(message = "active is required")
    private Boolean active;
}
