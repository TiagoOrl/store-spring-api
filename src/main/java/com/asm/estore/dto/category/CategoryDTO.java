package com.asm.estore.dto.category;

import lombok.Data;
import java.util.Date;

@Data
public class CategoryDTO {
    private int id;
    private String name;
    private Date createdAt;
    private Date updatedAt;
}
