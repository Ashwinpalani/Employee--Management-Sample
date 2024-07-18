package com.employee.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.employee.management.utility.EnvironmentConstants;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
//		DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class EmployeeManagementApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(EmployeeManagementApplication.class);
		application.run(EnvironmentConstants.EXTERNAL_PROPERTIES_FILE, EnvironmentConstants.LOGGING);
	}

}
