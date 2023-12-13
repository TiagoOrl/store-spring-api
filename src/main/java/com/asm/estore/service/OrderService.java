package com.asm.estore.service;

import com.asm.estore.dto.order.OrderDTO;
import com.asm.estore.entity.Order;
import com.asm.estore.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class OrderService {
    private final OrderRepository repository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> getAll() {
        return repository.findAll();
    }

    public List<OrderDTO> getAllByClientId(Long clientId) {
        Optional<List<Order>> opt = repository.findAllClientsOrders(clientId);
        if (opt.isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Clients has no Orders");
        return opt.get().stream().map(
                order -> mapper.map(order, OrderDTO.class)
        ).toList();
    }

    public void createNewOrder(Long clientId) {
        Optional<List<Order>> opt =  repository.findAllClientsOrders(clientId);
        opt.ifPresent(orders -> orders.forEach(
                order -> {
                    if (!order.getFinalized())
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Client has an Order not finalized if Id: " + order.getId());
                }
        ));
        Order order = new Order(clientId, 0.00F);
        repository.save(order);
    }
}
