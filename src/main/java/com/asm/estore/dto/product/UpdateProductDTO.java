package com.asm.estore.dto.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductDTO {

    private String name;
    private String description;
    private Float unitPrice;
    private Boolean active;
    private Integer unitsInStock;
}
