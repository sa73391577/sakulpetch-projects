package com.example.prototype.exceptions;

import java.io.IOException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		ErrorMessage message = new ErrorMessage(HttpStatus.FORBIDDEN.value(), new Date(), "Your user role does not have permission to access this section.",null);
		
		response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(message);
        response.getWriter().write(jsonResponse);
	}

}
