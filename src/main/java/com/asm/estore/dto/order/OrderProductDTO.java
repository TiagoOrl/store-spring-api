package com.asm.estore.dto.order;

import lombok.Data;

@Data
public class OrderProductDTO {
    private Integer amount;
    private Long fkOrderId;
    private Long fkProductId;
}
