package com.asm.estore.dto.product;

import lombok.Data;

import java.util.Date;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Float unitPrice;
    private String imageUrl;
    private boolean active;
    private int unitsInStock;
    private Date createdAt;
    private Date updatedAt;
    private Integer categoryId;
}
