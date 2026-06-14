package com.example.prototype.exceptions;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.example.prototype.entity.log.AccessErrorLogs;
import com.example.prototype.services.AccessErrorLogsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {
	
	@Autowired @Qualifier("AccessErrorLogsService_V1") AccessErrorLogsService accessErrorLogsService;
	
	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
	private final SimpleDateFormat formatterDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorMessage> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.FORBIDDEN.value(), new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.FORBIDDEN);
	}

	
	//Error : SQL and Access Database.
	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<ErrorMessage> dataAccessException(Exception ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), "DataAccessException.",
				"SQL or Database fail , please check.");
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//Error : All Error do not identify. ERROR 500
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleErrorException(Exception ex , HttpServletRequest http, WebRequest request) {
		//ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), ex.getMessage(),request.getDescription(false));
		Date now = new Date();
		String idErrorRecord = formatter.format(now);
		String apiPath = request.getDescription(false);

		log.debug(">>>> request.getContextPath() : "+ ToStringBuilder.reflectionToString(request) );
		log.debug(">>>> apiPath : "+ apiPath  );
		
	    Throwable rootCause = ex;
	    while (rootCause.getCause() != null) {
	        rootCause = rootCause.getCause();
	    }
	    StackTraceElement element = rootCause.getStackTrace()[0];
		String positionError = "Error at: " + element.getClassName() + "." + element.getMethodName() + ":" + element.getLineNumber();
		
		AccessErrorLogs err = new AccessErrorLogs();
		err.setDateError(now);
		err.setErrorIdRecord(idErrorRecord);
		err.setHttpMethod(http.getMethod());
		err.setUri(apiPath);
		err.setErrorStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		err.setErrorPosition(positionError);
		
		accessErrorLogsService.addAccessErrorLog(err);
		ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), now , "Server Error. id : "+idErrorRecord,"");
		ex.printStackTrace();
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
