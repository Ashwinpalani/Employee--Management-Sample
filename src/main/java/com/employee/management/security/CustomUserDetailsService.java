package com.employee.management.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.employee.management.domain.User;
import com.employee.management.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = userRepository.findByUsername(username);
			if (user == null) {
				throw new UsernameNotFoundException("User '" + username + "' not found");
			}

			GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					Collections.singleton(authority));
		} catch (Exception e) {
			throw new InternalAuthenticationServiceException("Error occurred while fetching user details", e);
		}
	}
}
