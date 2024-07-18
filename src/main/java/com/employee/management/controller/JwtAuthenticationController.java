package com.employee.management.controller;

import java.io.IOException;

import org.apache.hc.core5.http.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.employee.management.domain.User;
import com.employee.management.repository.UserRepository;
import com.employee.management.response.JwtRequest;
import com.employee.management.response.JwtResponse;
import com.employee.management.response.ResponseFormat;
import com.employee.management.service.TokenService;
import com.employee.management.utility.EnvironmentConstants;
import com.employee.management.utility.JwtTokenUtil;

@RestController
public class JwtAuthenticationController {

	private Logger log = LogManager.getLogger(JwtAuthenticationController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

//	@PostMapping("/authenticate")
//	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
//
//		ResponseFormat responseFormat = null;
//		try {
//			System.out.println("Credentials  ==> " + authenticationRequest.getUsername() + "  ===>  "
//					+ authenticationRequest.getPassword());
//			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
//			SecurityContextHolder.getContext().setAuthentication(authentication);
//			final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//			final String token = jwtTokenUtil.generateToken(userDetails);
//			responseFormat = new ResponseFormat(HttpStatus.OK.value(), HttpStatus.OK.name(), token);
//			return new ResponseEntity<>(responseFormat, HttpStatus.OK);
//		} catch (BadCredentialsException e) {
//			log.info(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
//			responseFormat = new ResponseFormat(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(),
//					"Invalid username or password");
//			return new ResponseEntity<>(responseFormat, HttpStatus.UNAUTHORIZED);
//
//		} catch (Exception e) {
//			log.error(LogUtility.getCurrentClassAndMethodName() + "===> " + LogUtility.getMessage(e));
//			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
//					HttpStatus.INTERNAL_SERVER_ERROR.name(), EnvironmentConstants.FAILURE);
//			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		ResponseFormat responseFormat = null;
		System.out.println("Credentials  ==> " + authenticationRequest.getUsername() + "  ===>  "
				+ authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails.getUsername());

		responseFormat = new ResponseFormat(HttpStatus.OK.value(), HttpStatus.OK.name(), token);
		return new ResponseEntity<>(responseFormat, HttpStatus.OK);
	}

	@PostMapping("/register")
	public User saveUser(@RequestBody User user) {
		//user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (Exception e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

//    @PostMapping("/register")
//    public User registerUser(@RequestBody User user) {
//            return userRepository.save(user);
//        }

	@GetMapping("/gettoken")
	public ResponseEntity<?> getToken(@RequestBody JwtRequest jwtRequest) {
		ResponseFormat responseFormat = null;
		String url = "http://localhost:8080/authenticate";
		String token = null;
		try {
			token = tokenService.authenticate(url, jwtRequest);
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS, token);
			return new ResponseEntity<>(responseFormat, HttpStatus.OK);
		} catch (IOException | ParseException e) {
			responseFormat = new ResponseFormat(HttpStatus.OK.value(), EnvironmentConstants.SUCCESS, token);
			responseFormat = new ResponseFormat(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.name(), EnvironmentConstants.FAILURE);
			return new ResponseEntity<>(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
