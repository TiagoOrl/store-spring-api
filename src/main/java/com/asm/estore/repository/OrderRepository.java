package com.asm.estore.repository;


import com.asm.estore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT order from Order order WHERE order.client.id = ?1")
    Optional<List<Order>> findAllClientsOrders(Long clientId);
}
