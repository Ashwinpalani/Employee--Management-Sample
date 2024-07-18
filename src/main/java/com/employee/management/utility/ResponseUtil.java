package com.employee.management.utility;

import java.io.IOException;

import com.employee.management.response.ResponseFormat;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

public class ResponseUtil {
	
    public static void writeJsonResponse(HttpServletResponse response, ResponseFormat responseFormat) throws IOException {
        response.setContentType("application/json");
        response.setStatus(responseFormat.getResponseCode());
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), responseFormat);
    }

}
