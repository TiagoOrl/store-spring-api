package com.asm.estore.dto.order;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDTO {
    private Integer id;
    private BigDecimal totalSum;
    private Date createdAt;
    private Date updatedAt;
    private Boolean finalized;
}
