package com.example.prototype.bean.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UserFormRequestBean {
	private String username;
	private String nameTH;
	private String surnameTH;
	private String nameEN;
	private String surnameEN;
	private String telephone;
	private String mobilePhone;
	private String idCard;
	private String email;
	private String genderCode;
	private String password;
	private String createdBy;
	private String registerId;
	private String brithDate;
	private List<String> roleCode;
}
