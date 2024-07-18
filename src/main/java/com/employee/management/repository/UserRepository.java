package com.employee.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.management.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
