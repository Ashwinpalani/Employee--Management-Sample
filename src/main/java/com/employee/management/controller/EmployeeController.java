package com.employee.management.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.management.domain.Employee;
import com.employee.management.repository.EmployeeRepository;
import com.employee.management.response.ResponseFormat;
import com.employee.management.utility.EnvironmentConstants;
import com.employee.management.utility.LogUtility;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	private Logger log = LogManager.getLogger(EmployeeController.class);

	
	@GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllEmployees(
			@RequestHeader(name = "apiRequestId", required = true) String apiRequestId) {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
				responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			List<Employee> employeeList = employeeRepository.findAll();
			if (employeeList.size() == 0 || employeeList == null) {
				responseFormat = new ResponseFormat(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
						employeeList);
				log.error(LogUtility.getCurrentClassAndMethodName() + "Employee List Not Found: " + employeeList);
				return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);
			}
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS, employeeList);
			//log.info(LogUtility.getCurrentClassAndMethodName() + "===> " + employeeList.toString());
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);
		} catch (Exception e) {
			log.error(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getEmployeeById(@PathVariable Long id,
			@RequestHeader(name = "apiRequestId", required = true) String apiRequestId) throws MethodArgumentNotValidException {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
				responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			Optional<Employee> employee = employeeRepository.findById(id);
			if (!employee.isPresent()) {
				responseFormat = new ResponseFormat(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
						employee);
				return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);
			}
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS, employee);
			log.info(LogUtility.getCurrentClassAndMethodName() + "===> " + employee.toString());
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);
		} catch (Exception e) {
			log.error(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<?> createEmployee(@RequestBody Employee employee,
	                                            @RequestHeader(name = "apiRequestId", required = true) String apiRequestId) {
	        log.info("createEmployee called with employee: " + employee);
	        ResponseFormat responseFormat = null;
	        try {
	            if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
	                responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
	                        EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
	                return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
	            }
	            Employee employeeData = employeeRepository.save(employee);
	            responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS_INSERT,
	                    employeeData);
	            log.info(LogUtility.getCurrentClassAndMethodName() + "Employee Created successfully: " + employeeData);
	            return new ResponseEntity<>(responseFormat, HttpStatus.OK);
	        } catch (Exception e) {
	            log.error(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
	            responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
	                    HttpStatus.INTERNAL_SERVER_ERROR.name(), EnvironmentConstants.FAILURE);
	            return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	
	@PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee,
			@RequestHeader(name = "apiRequestId", required = true) String apiRequestId) {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
				responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			Optional<Employee> employeeData = employeeRepository.findById(id);
			if (!employeeData.isPresent()) {
				responseFormat = new ResponseFormat(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
						employeeData);
				return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);
			}
			employeeData.get().setEmployeeId(employee.getEmployeeId());
			employeeData.get().setEmployeeName(employee.getEmployeeName());
			employeeData.get().setEmailId(employee.getEmailId());
			employeeData.get().setDesignation(employee.getDesignation());
			employeeData.get().setAccountNumber(employee.getAccountNumber());
			employeeData.get().setIsActive(employee.getIsActive());
			employeeData.get().setCompany(employee.getCompany());
			employeeData.get().setJoiningDate(employee.getJoiningDate());
			employeeData.get().setMobileNumber(employee.getMobileNumber());
			employeeData.get().setSalary(employee.getSalary() != null ? employee.getSalary() : employeeData.get().getSalary());
			employeeData.get().setAddress(employee.getSalary() != null ? employee.getAddress() : employeeData.get().getAddress());
			employeeData.get().setDepartment(employee.getDepartment() != null ? employee.getDepartment() : employeeData.get().getDepartment());
			employeeRepository.save(employeeData.get());
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS_UPDATE,
					employeeData);
			log.info(LogUtility.getCurrentClassAndMethodName() + "Employee Updated successfully: " + employeeData);
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);
		} catch (Exception e) {
			log.error(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteEmployee(@PathVariable Long id,
			@RequestHeader(name = "apiRequestId", required = true) String apiRequestId) {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
				responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			Optional<Employee> employee = employeeRepository.findById(id);
			if (!employee.isPresent()) {
				responseFormat = new ResponseFormat(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
						employee);
				return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);
			}
			employee.get().setIsActive("N");
			Employee employeeData = employeeRepository.save(employee.get());
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS_DELETE,
					employeeData);
			log.info(LogUtility.getCurrentClassAndMethodName() + "Employee deleted successfully: " + employeeData);
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);

		} catch (Exception e) {
			log.error(LogUtility.getCurrentClassAndMethodName() + " " + LogUtility.getMessage(e));
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@GetMapping("/getEmployeeByDepartment/{id}")
	public ResponseEntity<?> getAllEmployeesWithSalaryHistories(@PathVariable("id") Long id,@RequestHeader(name = "apiRequestId", required = true) String apiRequestId) {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
				responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			List<Employee> employeeList = employeeRepository.findByDepartmentId(id);
			if (employeeList.size() == 0 || employeeList == null) {
				responseFormat = new ResponseFormat(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
						employeeList);
				log.error(LogUtility.getCurrentClassAndMethodName() + "Employee List Not Found: " + employeeList);
				return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);
			}
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS, employeeList);
			//log.info(LogUtility.getCurrentClassAndMethodName() + "===> " + employeeList.toString());
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);
		} catch (Exception e) {
			log.error(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	

}
