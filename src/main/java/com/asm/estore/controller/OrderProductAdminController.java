package com.asm.estore.controller;


import com.asm.estore.dto.order.DeleteOrderProductDTO;
import com.asm.estore.entity.OrderProduct;
import com.asm.estore.service.OrderProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/admin/order_product")
public class OrderProductAdminController {
    private final OrderProductService orderProductService;

    @Autowired
    public OrderProductAdminController(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }


    @GetMapping
    public List<OrderProduct> getAllOrderProducts(
            Optional<Integer> page,
            Optional<Integer> size
    ) {
        return orderProductService.getAllOrderProducts(page, size);
    }
}
