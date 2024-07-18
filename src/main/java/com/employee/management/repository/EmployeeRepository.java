package com.employee.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.employee.management.domain.Department;
import com.employee.management.domain.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	List<Employee> getEmployeeByDepartment(Department department);
	
	@Query("SELECT e FROM Employee e LEFT JOIN FETCH e.salary")
    List<Employee> findAllWithSalaryHistories();
	
	
	List<Employee> findByDepartmentId(Long departmentId);
	
}
