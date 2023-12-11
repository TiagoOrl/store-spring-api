package com.asm.estore.controller;


import com.asm.estore.entity.Order;
import com.asm.estore.entity.OrderProduct;
import com.asm.estore.service.OrderProductService;
import com.asm.estore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/order")
public class OrdersController {

    private final OrderProductService orderProductService;
    private final OrderService orderService;

    @Autowired
    public OrdersController(OrderProductService service, OrderService orderService) {
        this.orderProductService = service;
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAll();
    }

    @GetMapping(path = "product")
    public List<OrderProduct> getAllOrderProducts() {
        return orderProductService.getAllOrderProducts();
    }

    @GetMapping(path = "product/{id}")
    public List<OrderProduct> getAllOrderProductsById(
            @PathVariable("id") Long id
    ) {
        return orderProductService.getByOrderId(id);
    }

}
