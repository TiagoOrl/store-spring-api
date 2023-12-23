package com.asm.estore.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteOrderProductDTO {
    @NotNull(message = "orderId is required")
    private Long fkOrderId;
    @NotNull(message = "productId is required")
    private Long fkProductId;
}
