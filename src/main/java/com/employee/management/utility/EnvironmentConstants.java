package com.employee.management.utility;

public class EnvironmentConstants {
	
	
	public static final String  EXTERNAL_PROPERTIES_FILE_LOCATION = "D:/EmployeeManagement/Configuration/external.properties";
	
	public static final String LOG_PATH = "D:/EmployeeManagement/Configuration/log4j2.xml";
	
	public static String tomcatHome = System.getProperty("catalina.home");
	 
	 public static final String TOMCAT_EXTERNAL_PROPERTIES_FILE = tomcatHome +"/conf/EmployeeManagement/external.properties";
	 public static final String TOMCAT_LOG_PATH = tomcatHome + "/conf/EmployeeManagement/log4j2.xml";
	
	public static final String EXTERNAL_PROPERTIES_FILE = "--spring.config.location ="+ EXTERNAL_PROPERTIES_FILE_LOCATION;
	
	public static final String LOGGING = "--logging.config = "+ LOG_PATH;
	
	public static final String SUCCESS="Success";
	public static final String FAILURE ="Failure";
	
	public static final String SUCCESS_FOUND ="Records Found";
	public static final String SUCCESS_INSERT ="Records Inserted Successfully";
	public static final String SUCCESS_UPDATE ="Records Updated Successfully";
	public static final String SUCCESS_DELETE = "Records Deleted Successfully";
	public static final String NOTFOUND="Not Found";
	public static final String MANDATORY_PARAM="Mandatory Parameter Missing";
	public static final String INVALID_DATA="Invalid Data";
	public static final String GENERAL_FAILURE="General Failure";
	public static final String API_REQUEST_ID ="apiRequestId cannot be null or empty";
	public static final String BAD_REQUEST  = "BAD_REQUEST";
	
	

}
