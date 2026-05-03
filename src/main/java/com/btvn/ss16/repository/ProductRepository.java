package com.btvn.ss16.repository;

import com.btvn.ss16.model.Category;
import com.btvn.ss16.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findByCategory(Category category);

    long countByCategory(Category category);

    List<Product> findByNameContainingIgnoreCase(String name);
}
