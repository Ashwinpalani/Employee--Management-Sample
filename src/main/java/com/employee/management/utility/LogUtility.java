package com.employee.management.utility;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.context.annotation.Configuration;

@Configuration
public class LogUtility {
	
	public static String getCurrentClassAndMethodName() {
	    final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
	    final String className = e.getClassName();
	    final String methodName = e.getMethodName();
	    final int lineNumber = e.getLineNumber();

	    System.out.println("Class: " + className);
	    System.out.println("Method: " + methodName);
	    System.out.println("Line number: " + lineNumber);

	    return String.format("@%d [%s].[%s]", lineNumber, className.substring(className.lastIndexOf('.') + 1), methodName);
	    		
	}
	
	public static String getMessage(Exception ex) {
		StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
	}


}



