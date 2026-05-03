package com.btvn.ss16.repository;

import com.btvn.ss16.model.Order;
import com.btvn.ss16.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderByOrderedAtDesc();

    List<Order> findByUserOrderByOrderedAtDesc(User user);
}
