package com.asm.estore.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOrderProductDTO {
    @NotNull(message = "amount is required")
    @Min(value = 0, message = "required value for amount should be >= 0")
    private Integer amount;

    @NotNull(message = "orderId is required")
    private Long fkOrderId;

    @NotNull(message = "productId is required")
    private Long fkProductId;
}
