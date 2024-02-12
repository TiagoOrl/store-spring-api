package com.asm.estore.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SearchProductDTO {
    @NotBlank(message = "product name is required")
    @Size(min = 0, max = 120)
    String name;
}
