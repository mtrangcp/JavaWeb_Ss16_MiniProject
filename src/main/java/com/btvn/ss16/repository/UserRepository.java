package com.btvn.ss16.repository;

import com.btvn.ss16.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
