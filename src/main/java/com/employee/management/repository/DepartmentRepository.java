package com.employee.management.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.employee.management.domain.*;

public interface DepartmentRepository extends JpaRepository<Department,Long> {

}
