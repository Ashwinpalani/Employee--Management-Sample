package com.employee.management.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseFormat {

	private int  responseCode;

	private String responseStatus;
	
	private String responseMessage;
	
	private Object responseData;

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public ResponseFormat(int  responseCode, String responseStatus, Object responseData) {
		super();
		this.responseCode = responseCode;
		this.responseStatus = responseStatus;
		this.responseData = responseData;
	}
	
	public ResponseFormat(int  responseCode, String responseStatus, String responseMessage) {
		super();
		this.responseCode = responseCode;
		this.responseStatus = responseStatus;
		this.responseMessage = responseMessage;
	}


	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public Object getResponseData() {
		return responseData;
	}

	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}

	
	
}
