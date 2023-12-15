package com.asm.estore.controller;


import com.asm.estore.dto.order.OrderDTO;
import com.asm.estore.dto.order.OrderProductDTO;
import com.asm.estore.entity.Order;
import com.asm.estore.entity.OrderProduct;
import com.asm.estore.service.OrderProductService;
import com.asm.estore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
public class OrdersController {
    private final OrderService orderService;

    @Autowired
    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAll();
    }

    @GetMapping("client/{clientId}")
    public List<OrderDTO> getOrdersByClientId(@PathVariable("clientId") Long clientId) {
        return orderService.getAllByClientId(clientId);
    }

    @GetMapping("{orderId}")
    public Order getOrderById(@PathVariable("orderId") Long id) {
        return orderService.getByOrderId(id);
    }

    @PostMapping(path = "create/{clientId}")
    public OrderDTO createOrderByClientId(@PathVariable("clientId") Long id) {
        return orderService.createNewOrder(id);
    }

    @PutMapping(path = "finalize/{orderId}")
    public OrderDTO finalizeOrder(@PathVariable("orderId") Long orderId) {
        return orderService.finalizeOrder(orderId);
    }

}
