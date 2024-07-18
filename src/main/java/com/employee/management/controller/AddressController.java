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

import com.employee.management.domain.Address;
import com.employee.management.repository.AddressRepository;
import com.employee.management.response.ResponseFormat;
import com.employee.management.utility.EnvironmentConstants;
import com.employee.management.utility.LogUtility;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/address")
public class AddressController {

	Logger log = LogManager.getLogger(AddressController.class);

	@Autowired
	private AddressRepository addressRepository;

	@GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAddress(@RequestHeader(name = "apiRequestId", required = true) String apiRequestId) {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
				responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			List<Address> addressList = addressRepository.findAll();
			if (addressList.size() == 0 || addressList == null) {
				responseFormat = new ResponseFormat(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
						addressList);
				return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);
			}
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS, addressList);
			log.info(LogUtility.getCurrentClassAndMethodName() + "===> " + addressList);
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);
		} catch (Exception e) {
			log.error(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping(value = "/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAddressById(@PathVariable Long id ,@RequestHeader(name = "apiRequestId", required = true) String apiRequestId) {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
				responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			Optional<Address> address = addressRepository.findById(id);
			if (!address.isPresent()) {
				responseFormat = new ResponseFormat(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
						address);
				return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);
			}
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS, address);
			log.info(LogUtility.getCurrentClassAndMethodName() + "===> " + address);
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);
		} catch (Exception e) {
			log.error(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAddress(@RequestBody Address address,
			@RequestHeader(name = "apiRequestId", required = true) String apiRequestId) {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
				responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			Address addressData = addressRepository.save(address);
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS_INSERT,
					addressData);
			log.info(LogUtility.getCurrentClassAndMethodName() + "Address created succesfully: " + addressData);
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);
		} catch (Exception e) {
			log.error(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateAddress(@PathVariable Long id, @Valid @RequestBody Address address,
			@RequestHeader(name = "apiRequestId", required = true) String apiRequestId) {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
				responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			Optional<Address> addressTemplate = addressRepository.findById(id);
			if (!addressTemplate.isPresent()) {
				responseFormat = new ResponseFormat(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
						addressTemplate);
				log.error(LogUtility.getCurrentClassAndMethodName() + "Address Not Found: " + responseFormat);
				return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);
			}
			addressTemplate.get().setCity(address.getCity()!= null ? address.getCity(): addressTemplate.get().getCity());
			addressTemplate.get().setState(address.getState()!= null ? address.getState(): addressTemplate.get().getState());
			addressTemplate.get().setPincode(address.getPincode()!= null ? address.getPincode(): addressTemplate.get().getPincode());
			Address addressData = addressRepository.save(addressTemplate.get());
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS_UPDATE,
					addressData);
			log.info(LogUtility.getCurrentClassAndMethodName() + "Address Updated succesfully: " + addressData);
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);
		} catch (Exception e) {
			log.error(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(),EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteAddress(@PathVariable Long id,
			@RequestHeader(name = "apiRequestId", required = true) String apiRequestId) {
		ResponseFormat responseFormat = null;
		try {
			if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
				responseFormat = new ResponseFormat(HttpStatus.BAD_REQUEST.value(),
						EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
				return new ResponseEntity<>(responseFormat, HttpStatus.BAD_REQUEST);
			}
			Optional<Address> addressTemplate = addressRepository.findById(id);
			if (!addressTemplate.isPresent()) {
				responseFormat = new ResponseFormat(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
						addressTemplate);
				return new ResponseEntity<>(responseFormat, HttpStatus.NOT_FOUND);
			}
			addressRepository.deleteById(id);
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS_DELETE,
					addressTemplate.get());
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);
		} catch (Exception e) {
			log.error(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
