package com.asm.estore.controller;


import com.asm.estore.dto.order.OrderDTO;
import com.asm.estore.dto.order.OrderProductDTO;
import com.asm.estore.entity.Order;
import com.asm.estore.entity.OrderProduct;
import com.asm.estore.service.OrderProductService;
import com.asm.estore.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/order")
public class OrdersController {
    private final OrderService orderService;

    @Autowired
    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDTO> getAllOrders(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        return orderService.getAll(page, size);
    }

    @GetMapping("client/{clientId}")
    public List<OrderDTO> getOrdersByClientId(@PathVariable("clientId") Long clientId) {
        return orderService.getAllByClientId(clientId);
    }

    @GetMapping("{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("orderId") Long id) {
        return ResponseEntity.ok(orderService.getByOrderId(id));
    }

    @PostMapping(path = "create/{clientId}")
    public OrderDTO createOrderByClientId(
            @PathVariable("clientId") Long id
    ) {
        return orderService.createNewOrder(id);
    }

    @PutMapping(path = "finalize/{orderId}")
    public OrderDTO finalizeOrder(
            @PathVariable("orderId") Long orderId
    ) {
        return orderService.finalizeOrder(orderId);
    }

}
