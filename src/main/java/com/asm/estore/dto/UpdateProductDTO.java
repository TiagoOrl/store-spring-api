package com.asm.estore.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductDTO {

    private String name;
    private String description;
    private BigDecimal unitPrice;
    private Boolean active;
    private Integer stockCount;
}
