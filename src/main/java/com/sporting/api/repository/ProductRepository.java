package com.sporting.api.repository;

import com.sporting.api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductId(String uuid);

    List<Product> findByCategoryId(String categoryId);
}
