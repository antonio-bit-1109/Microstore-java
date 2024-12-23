package org.example.microstoreprogetto.PRODUCTS.repository;

import org.example.microstoreprogetto.PRODUCTS.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {


    @Query("SELECT count(id) FROM Products  WHERE name = :prodName")
    int findByName(@Param("prodName") String prodName);

    List<Products> getProductsById(Long id);
}
