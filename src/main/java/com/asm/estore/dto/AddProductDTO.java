package com.asm.estore.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductDTO {

    private String name;
    private String description;
    private BigDecimal unitPrice;
    private String imageUrl;
    private Boolean active;
    private Integer unitsInStock;
    private Integer categoryId;
}
