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

import com.employee.management.domain.SalaryHistory;
import com.employee.management.repository.SalaryHistoryRepository;
import com.employee.management.response.ResponseFormat;
import com.employee.management.utility.EnvironmentConstants;
import com.employee.management.utility.LogUtility;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/salary")
public class SalaryHistoryController {

	Logger log = LogManager.getLogger(SalaryHistoryController.class);

	@Autowired
	SalaryHistoryRepository salaryHistoryRepository;

	@GetMapping(value = "/getSalaryHistoryById/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getSalaryByEmployeeId(@PathVariable Long id,
			@RequestHeader(name = "apiRequestId", required = true) String apiRequestId) {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
				responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			Optional<SalaryHistory> salaryHistory = salaryHistoryRepository.findById(id);
			if (!salaryHistory.isPresent()) {
				responseFormat = new ResponseFormat(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
						salaryHistory);
				return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);
			}
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS , salaryHistory);
			
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);
		} catch (Exception e) {
			log.error(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(),  EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getSalary(@RequestHeader(name = "apiRequestId", required = true) String apiRequestId) {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
				responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			List<SalaryHistory> salaryHistory = salaryHistoryRepository.findAll();
			if (salaryHistory.size() == 0 || salaryHistory == null) {
				responseFormat = new ResponseFormat(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
						salaryHistory);
				return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);
			}
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS , salaryHistory);
			log.info(LogUtility.getCurrentClassAndMethodName() + "Salary List : " + salaryHistory);
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);
		} catch (Exception e) {
			log.error(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addSalary(@RequestHeader(name = "apiRequestId", required = true) String apiRequestId,
			@RequestBody SalaryHistory salaryHistory) {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
				responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			salaryHistoryRepository.save(salaryHistory);
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS_INSERT, salaryHistory);
			log.info(LogUtility.getCurrentClassAndMethodName() + "Salary Created Successfully : " + salaryHistory);
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);

		} catch (Exception e) {
			log.error(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(),  EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateSalary(@RequestHeader(name = "apiRequestId", required = true) String apiRequestId,
			@PathVariable Long id,@Valid @RequestBody SalaryHistory salaryHistory) {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
				responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			Optional<SalaryHistory> salary = salaryHistoryRepository.findById(id);
			if (!salary.isPresent()) {
				responseFormat = new ResponseFormat(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
						salary);
				return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);
			}
			salary.get().setSalary(salaryHistory.getSalary());
			salaryHistoryRepository.save(salary.get());
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS_UPDATE , salaryHistory);
			log.info(LogUtility.getCurrentClassAndMethodName() + "Salary Updated Successfully : " + salaryHistory);
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);

		} catch (Exception e) {
			log.error(LogUtility.getCurrentClassAndMethodName() + "==> " + LogUtility.getMessage(e));
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteSalaryById(
			@RequestHeader(name = "apiRequestId", required = true) String apiRequestId, @PathVariable Long id) {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
				responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			Optional<SalaryHistory> salary = salaryHistoryRepository.findById(id);
			if (!salary.isPresent()) {
				responseFormat = new ResponseFormat(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
						salary);
				return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);	
			}	
			salaryHistoryRepository.deleteById(id);
			log.info(LogUtility.getCurrentClassAndMethodName() + "Salary Deleted Successfully");
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
