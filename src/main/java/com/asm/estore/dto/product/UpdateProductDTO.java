package com.asm.estore.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductDTO {
    @Size(min = 3, max = 200, message = "name is invalid")
    private String name;

    @Size(min = 4, max = 600, message = "description is invalid")
    private String description;

    @Min(value = 0, message = "unitPrice should be >= 0")
    private Float unitPrice;

    @Size(min = 6, max = 350, message = "imageUrl is invalid")
    private String imageUrl;

    @Min(value = 0, message = "unitsInStock should be >= 0")
    private Integer unitsInStock;

    private Boolean active;

    private Long categoryId;
}
