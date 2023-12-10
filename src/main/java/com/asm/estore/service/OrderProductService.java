package com.asm.estore.service;

import com.asm.estore.entity.OrderProduct;
import com.asm.estore.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class OrderProductService {
    private final OrderProductRepository repository;

    @Autowired
    public OrderProductService(OrderProductRepository repository) {
        this.repository = repository;
    }

    public List<OrderProduct> getAll() {
        return repository.findAll();
    }

    public List<OrderProduct> getByOrderId(Long id) {
        Optional<List<OrderProduct>> opt = repository.findByOrderId(id);

        if (opt.isEmpty() || opt.get().isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Found no order_product with this Order ID");

        return opt.get();

    }
}
