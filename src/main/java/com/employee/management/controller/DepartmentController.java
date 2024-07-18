package com.employee.management.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.management.domain.Department;
import com.employee.management.repository.DepartmentRepository;
import com.employee.management.response.ResponseFormat;
import com.employee.management.utility.EnvironmentConstants;
import com.employee.management.utility.LogUtility;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

	private Logger log = LogManager.getLogger(DepartmentController.class);

	@Autowired
	private DepartmentRepository departmentRepository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllDepartments(
			@RequestHeader(name = "apiRequestId", required = true) String apiRequestId) {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
			responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			List<Department> department = departmentRepository.findAll();
			if (department.size() == 0 || department == null) {
				responseFormat = new ResponseFormat(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
						department);
				log.error(LogUtility.getCurrentClassAndMethodName() + "Department List Not Found: " + department);
				return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);
			}
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS , department);
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);
		} catch (Exception e) {
			log.error(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getDepartmentById(@PathVariable Long id,
			@RequestHeader(name = "apiRequestId", required = true) String apiRequestId) {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
			responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			Optional<Department> departmentOptional = departmentRepository.findById(id);
			if (!departmentOptional.isPresent()) {
				responseFormat = new ResponseFormat(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),EnvironmentConstants.FAILURE);
				return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);
			}
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS , departmentOptional.get());
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);
			
		} catch (Exception e) {
			log.error(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createDepartment(@RequestBody Department department,@RequestHeader(name = "apiRequestId", required = true) String apiRequestId) {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
			responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			Department departments = departmentRepository.save(department);
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS_INSERT,
					departments);
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);
		} catch (Exception e) {
			log.error(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateDepartment(@PathVariable Long id,@Valid @RequestBody Department departmentDetails,
			@RequestHeader(name = "apiRequestId", required = true) String apiRequestId) {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
			responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			Optional<Department> department = departmentRepository.findById(id);
			if (!department.isPresent()) {
				responseFormat = new ResponseFormat(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), EnvironmentConstants.FAILURE);
				return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);
			}
			department.get().setDepartmentName(departmentDetails.getDepartmentName());
			Department updatedDepartment = departmentRepository.save(department.get());
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), "Success", updatedDepartment);
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);
		} catch (Exception e) {
			log.error(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteDepartment(@PathVariable Long id,
			@RequestHeader(name = "apiRequestId", required = true) String apiRequestId) {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
			responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			Optional<Department> department = departmentRepository.findById(id);
			if (!department.isPresent()) {
				responseFormat = new ResponseFormat(HttpStatus.NOT_FOUND.value(), "Department is not available",EnvironmentConstants.FAILURE);
				return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);
			}
			departmentRepository.deleteById(id);
			responseFormat = new ResponseFormat(HttpStatus.OK.value(),EnvironmentConstants.SUCCESS ,EnvironmentConstants.SUCCESS_DELETE);
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);
		} catch (Exception e) {
			log.error(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
