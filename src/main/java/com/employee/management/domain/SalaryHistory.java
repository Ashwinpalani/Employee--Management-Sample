package com.employee.management.domain;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class SalaryHistory extends AuditModel{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotNull(message = "Salary cannot be null")
	private Double salary;

	@CreationTimestamp	
	private LocalDate effectiveStartDate;

//	@ManyToOne
//	@JoinColumn(name = "employee_id")
//    @JsonBackReference
//	private Employee employee;
	
	public SalaryHistory() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public LocalDate getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(LocalDate effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}
	
//	public Employee getEmployee() {
//		return employee;
//	}

//	public void setEmployee(Employee employee) {
//		this.employee = employee;
//	}

	@Override
	public String toString() {
		return "SalaryHistory [id=" + id + ", salary=" + salary + ", effectiveStartDate=" + effectiveStartDate
				+ ", employee="  + "]";
	}


	



}
