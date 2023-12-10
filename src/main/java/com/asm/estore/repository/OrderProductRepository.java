package com.asm.estore.repository;

import com.asm.estore.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    @Query("SELECT op FROM OrderProduct op WHERE op.fkOrderId = ?1")
    Optional<List<OrderProduct>> findByOrderId(Long id);
}
