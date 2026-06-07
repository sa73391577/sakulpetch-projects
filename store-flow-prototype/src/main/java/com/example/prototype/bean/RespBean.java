package com.example.prototype.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RespBean {
	private Integer statusCode;
	private String message;
	private Object data;
}
