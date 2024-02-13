package com.asm.estore.repository;

import com.asm.estore.dto.client.SingleClientDTO;
import com.asm.estore.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component // annotation for DI
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c from Client c WHERE c.email = ?1")
    Optional<Client> findByEmail(String email);

    @Query("SELECT c from Client c WHERE c.countryId = ?1")
    Optional<Client> findByCountryId(String countryId);

    @Query("SELECT c from Client c WHERE UPPER(c.fullname) LIKE CONCAT('%', UPPER(?1), '%') ")
    List<Client> getAllByName(String name);
}
