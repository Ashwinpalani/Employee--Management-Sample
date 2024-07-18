package com.employee.management.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiInterceptorConfig implements WebMvcConfigurer {

	@Autowired
	private ApiInterceptor apiInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(apiInterceptor).addPathPatterns("/api/**"); // Apply to all paths
	}
}
