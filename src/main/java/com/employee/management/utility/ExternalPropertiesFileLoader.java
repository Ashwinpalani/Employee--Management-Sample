//package com.employee.management.utility;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.Properties;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.core.env.ConfigurableEnvironment;
//import org.springframework.core.env.PropertiesPropertySource;
//
//import com.employee.management.controller.EmployeeController;
//
//public class ExternalPropertiesFileLoader implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
//	
//	Logger log = LogManager.getLogger(EmployeeController.class);
//
//	    @Override
//	    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
//	        ConfigurableEnvironment environment = event.getEnvironment();
////	        String tomcatHome = System.getProperty("catalina.home");
////	        String propertiesFilePath = tomcatHome + "/conf/config.properties";
//	        String propertiesFilePath = EnvironmentConstants.EXTERNAL_PROPERTIES_FILE_LOCATION;
//
//	        Properties properties = new Properties();
//	        try (FileInputStream input = new FileInputStream(propertiesFilePath)) {
//	            properties.load(input);
//	            PropertiesPropertySource propertySource = new PropertiesPropertySource("externalConfig", properties);
//	            environment.getPropertySources().addFirst(propertySource);
//	            propertySource.getProperty("spring.datasource.url");
//	            System.out.println("Property connected " +  propertySource.getProperty("spring.datasource.url"));
//	            log.debug(LogUtility.getCurrentClassAndMethodName() + "====> " +  propertySource.getProperty("spring.datasource.url"));
//	        } catch (IOException e) {
//	        	 log.error(LogUtility.getCurrentClassAndMethodName() + "====> " + LogUtility.getMessage(e));
//	          //  e.printStackTrace();
//	           
//	        }
//	    }
//	    
//}
//
