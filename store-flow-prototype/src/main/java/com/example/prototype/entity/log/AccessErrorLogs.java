package com.example.prototype.entity.log;

import java.util.Date;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="access_error_logs")
public class AccessErrorLogs {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="error_id_record")
	private String errorIdRecord;
	
	@Column(name="date_error")
	private Date dateError;
	
	@Column(name="http_method")
	private String httpMethod;
	
	@Column(name="uri")
	private String uri;
	
	@Column(name="error_status_code")
	private String errorStatusCode;
	
	@Column(name="error_position")
	private String errorPosition;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getErrorIdRecord() {
		return errorIdRecord;
	}

	public void setErrorIdRecord(String errorIdRecord) {
		this.errorIdRecord = errorIdRecord;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getErrorStatusCode() {
		return errorStatusCode;
	}

	public void setErrorStatusCode(String errorStatusCode) {
		this.errorStatusCode = errorStatusCode;
	}

	public String getErrorPosition() {
		return errorPosition;
	}

	public void setErrorPosition(String errorPosition) {
		this.errorPosition = errorPosition;
	}

	public Date getDateError() {
		return dateError;
	}

	public void setDateError(Date dateError) {
		this.dateError = dateError;
	}
	
}
