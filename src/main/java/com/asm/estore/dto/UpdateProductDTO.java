package com.asm.estore.dto;

import java.math.BigDecimal;

public class UpdateProductDTO {
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Boolean isActive() {
        return isActive;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    private final String name;
    private final String description;
    private final BigDecimal unitPrice;
    private final Boolean isActive;
    private final Integer stockCount;

    public UpdateProductDTO(String name, String description, BigDecimal unitPrice, boolean isActive, int stockCount) {
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.isActive = isActive;
        this.stockCount = stockCount;
    }
}
