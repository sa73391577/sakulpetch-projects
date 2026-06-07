package com.example.prototype.exceptions;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@JsonInclude(Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
	private int statusCode;
	private Date timestamp;
	private String message;
	private String description;
}
