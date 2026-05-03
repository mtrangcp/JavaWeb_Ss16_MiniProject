package com.btvn.ss16.repository;

import com.btvn.ss16.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
