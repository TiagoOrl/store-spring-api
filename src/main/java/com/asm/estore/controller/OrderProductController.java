package com.asm.estore.controller;


import com.asm.estore.dto.order.DeleteOrderProductDTO;
import com.asm.estore.dto.order.OrderProductDTO;
import com.asm.estore.dto.order.UpdateOrderProductDTO;
import com.asm.estore.entity.OrderProduct;
import com.asm.estore.service.OrderProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user/order_product")
public class OrderProductController {
    private final OrderProductService orderProductService;

    @Autowired
    public OrderProductController(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }


    @GetMapping(path = "{orderId}")
    public List<OrderProductDTO> getAllByOrderId(@PathVariable("orderId") Long id) {
        return orderProductService.getAllByOrderId(id);
    }

    @PostMapping(path = "add")
    public OrderProductDTO addProductToOrder(
            @Valid @RequestBody OrderProductDTO dto
    ) {
        return orderProductService.addOrderProduct(dto);
    }

    @PutMapping(path = "update_amount")
    public UpdateOrderProductDTO changeProductAmount(
            @Valid @RequestBody UpdateOrderProductDTO dto
    ) {
        return orderProductService.changeAmount(dto);
    }

    @DeleteMapping(path = "delete")
    public DeleteOrderProductDTO deleteOrderProduct(
            @Valid @RequestBody DeleteOrderProductDTO dto
    ) {
        return orderProductService.deleteByOrderIdProductId(dto);
    }
}
