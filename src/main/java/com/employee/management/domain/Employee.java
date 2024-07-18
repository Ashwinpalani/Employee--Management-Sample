package com.employee.management.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostPersist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Entity
public class Employee extends AuditModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String employeeId;

	@Column(nullable = false)
	@NotBlank(message = "Employee name cannot be blank")
	private String employeeName;

	@Column(nullable = false, unique = true)
	@Email(message = "Email should be valid")
	@NotBlank(message = "Email cannot be blank")
	private String emailId;

	@Column(nullable = false)
	@NotBlank(message = "Mobile number cannot be blank")
	private String mobileNumber;

	@Column(nullable = false)
	@NotBlank(message = "Company cannot be blank")
	private String company;

	@Column(nullable = false)
	@NotBlank(message = "Designation cannot be blank")
	private String designation;

	@Column(nullable = false)
	@NotBlank(message = "Joining date cannot be blank")
	private String joiningDate;

	@Column(nullable = false)
	@NotBlank(message = "Account number cannot be blank")
	private String accountNumber;

	@Column(nullable = false)
	private String isActive;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_id") 
	private List<SalaryHistory> salary;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;

	@PostPersist
	public void generateEmployeeId() {
		if (employeeId == null && id != null) {
			this.employeeId = "EMP" + String.format("%05d", this.id);
		}
	}

	public Employee() {
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<SalaryHistory> getSalary() {
		return salary;
	}

	public void setSalary(List<SalaryHistory> salary) {
		this.salary = salary;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	

//	public void addSalaryHistory(SalaryHistory salaryHistory) {
//		this.salary.add(salaryHistory);
//		salaryHistory.setEmployee(this);
//	}
//
//	public void removeSalaryHistory(SalaryHistory salaryHistory) {
//		this.salary.remove(salaryHistory);
//		salaryHistory.setEmployee(null);
//	}


	@Override
	public String toString() {
		return "Employee{" + "id=" + id + ", employeeName='" + employeeName + '\'' + ", emailId='" + emailId + '\''
				+ ", mobileNumber='" + mobileNumber + '\'' + ", company='" + company + '\'' + ", designation='"
				+ designation + '\'' + ", joiningDate='" + joiningDate + '\'' + ", accountNumber='" + accountNumber
				+ '\'' + ", isActive='" + isActive + '\'' + ", department="
				+ (department != null ? department.getId() : null) + '}';
	}
}
