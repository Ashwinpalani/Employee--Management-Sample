package com.employee.management.security;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.employee.management.utility.JwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	
	public JwtRequestFilter(){
		
	}
	Logger log = LogManager.getLogger(JwtRequestFilter.class);

	
	private UserDetailsService userDetailsService;
	private JwtTokenUtil jwtTokenUtil;

	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		 if (requestTokenHeader != null) {
	            jwtToken = requestTokenHeader.substring(7);
	            try {
	                username = jwtTokenUtil.extractUsername(jwtToken);
	            } catch (IllegalArgumentException e) {
	                logger.error("Unable to get JWT Token");
	            } catch (ExpiredJwtException e) {
	                logger.error("JWT Token has expired");
	            }
	        }

	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

	            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

	            if (jwtTokenUtil.validateToken(jwtToken, userDetails.getUsername())) {

	                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
	                        userDetails, null, userDetails.getAuthorities());
	                usernamePasswordAuthenticationToken
	                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	            }
	        }
		chain.doFilter(request, response);
	}
}