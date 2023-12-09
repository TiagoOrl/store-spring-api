package com.asm.estore.repository;

import com.asm.estore.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component // annotation for DI
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
