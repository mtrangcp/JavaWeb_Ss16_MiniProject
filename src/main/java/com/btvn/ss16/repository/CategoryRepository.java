package com.btvn.ss16.repository;

import com.btvn.ss16.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
