package com.asm.estore.repository;

import com.asm.estore.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Component // annotation for DI
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query("SELECT cat FROM ProductCategory cat WHERE UPPER(cat.name) = ?1")
    Optional<ProductCategory> findByName(String name);
}
