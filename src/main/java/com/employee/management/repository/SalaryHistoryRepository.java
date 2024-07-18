package com.employee.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.management.domain.SalaryHistory;

public interface SalaryHistoryRepository extends JpaRepository<SalaryHistory, Long>{

}
