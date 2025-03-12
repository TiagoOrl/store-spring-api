package com.asm.estore.repository;

import com.asm.estore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Component // annotation for DI
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name = ?1")
    Optional<Product> findProductByName(String name);

    @Query("SELECT p FROM Product p WHERE p.id = ?1")
    Optional<Product> findProductById(Long id);

    @Query("SELECT p FROM Product p WHERE UPPER(p.name) LIKE CONCAT('%',UPPER(:name),'%')")
    Optional<List<Product>> findAllByName(@Param("name") String name, Pageable pageable);

//    @Query(value = "SELECT * from product WHERE name LIKE %:name%", nativeQuery = true)
//    Optional<List<Product>> findAllByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT count(p) FROM Product p WHERE UPPER(p.name) LIKE CONCAT('%', UPPER(:name), '%')")
    Integer countByNameLike(@Param("name") String name);

    @Query("SELECT p FROM Product p WHERE p.category.id = ?1")
    Optional<List<Product>> findByCatId(Long catId, Pageable pageable);

    @Query(value = "SELECT count(*) from product WHERE fk_category_id = ?1", nativeQuery = true)
    Integer countByCategoryId(Long catId);
}