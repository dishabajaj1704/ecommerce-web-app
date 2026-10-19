package com.ecommerce.product.repository;

import com.ecommerce.product.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long> {
    // Custom query to find all active products
    //Syntax: findBy[ColumnName][Value]
    List<Product> findByActiveTrue() ;


    //Why we didn't write directly '%keyword%' in the query?
    //Because we want to prevent SQL injection attacks and ensure that the keyword is properly escaped.
    //Using CONCAT in combination with parameter binding helps achieve this.
    @Query("SELECT p FROM products p WHERE p.active = true AND p.stockQuantity > 0 AND" +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Product> searchProducts(@Param("keyword") String keyword);
}
