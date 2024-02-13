package com.asm.estore.dto.order;
import com.asm.estore.dto.product.ProductDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class OrderDTO implements Serializable {
    private Integer id;
    private BigDecimal totalSum;
    private Date createdAt;
    private Date updatedAt;
    private Boolean finalized;
    private Set<ProductDTO> products;
}
