package com.employee.management.response;

import java.io.Serializable;

public class JwtResponse implements Serializable {
	private int responseCode;
	private String responseStatus;
	private String responseMessage;

	public JwtResponse() {
	}

	public JwtResponse(int responseCode, String responseStatus, String responseMessage) {
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

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseData(String responseMessage) {
		this.responseMessage = responseMessage;
	}

}
