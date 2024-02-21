package com.asm.estore.controller;


import com.asm.estore.dto.order.OrderDTO;
import com.asm.estore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/admin/order")
public class OrdersAdminController {
    private final OrderService orderService;

    @Autowired
    public OrdersAdminController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    public List<OrderDTO> getAllOrders(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        return orderService.getAll(page, size);
    }

    @GetMapping("{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("orderId") Long id) {
        return ResponseEntity.ok(orderService.getByOrderId(id));
    }
}
