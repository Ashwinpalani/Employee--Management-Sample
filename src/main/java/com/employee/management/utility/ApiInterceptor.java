package com.employee.management.utility;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.employee.management.response.ResponseFormat;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		final String apiRequestId = request.getHeader("apiRequestId");
		System.out.println("Header  =====> "+ apiRequestId);
		if (apiRequestId == null || apiRequestId.trim().isEmpty()) {
			ResponseFormat responseFormat = new ResponseFormat(HttpServletResponse.SC_BAD_REQUEST,
					EnvironmentConstants.BAD_REQUEST, EnvironmentConstants.API_REQUEST_ID);
			ResponseUtil.writeJsonResponse(response, responseFormat);
		} else {
			ThreadContext.put("apiRequestId",request.getHeader("apirequestid"));
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		ThreadContext.remove("apiRequestId");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {
	}

}
