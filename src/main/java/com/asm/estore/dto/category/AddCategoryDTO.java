package com.asm.estore.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddCategoryDTO {
    @NotBlank(message = "name is required")
    String name;
}
