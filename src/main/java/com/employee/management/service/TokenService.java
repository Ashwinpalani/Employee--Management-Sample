package com.employee.management.service;

import java.io.IOException;

import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Service;

import com.employee.management.response.JwtRequest;
import com.employee.management.response.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TokenService {
	
	private static final CloseableHttpClient httpClient = HttpClients.createDefault();
	private static final ObjectMapper objectMapper = new ObjectMapper();

	 public String authenticate(String authUrl, JwtRequest jwtRequest) throws IOException, ParseException {
	        HttpPost postRequest = new HttpPost(authUrl);
	        postRequest.setHeader("Content-Type", "application/json");
	        postRequest.setHeader("Accept", "application/json");

	        String json = objectMapper.writeValueAsString(jwtRequest);
	        postRequest.setEntity(new StringEntity(json));

	        try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
	            if (response.getCode() != 200) {
	                throw new HttpResponseException(response.getCode(), "Failed to authenticate: " + response.getCode());
	            }
	            String responseString = EntityUtils.toString(response.getEntity());
	            JwtResponse jwtResponse = objectMapper.readValue(responseString, JwtResponse.class);
	            return jwtResponse.getResponseMessage();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

}
