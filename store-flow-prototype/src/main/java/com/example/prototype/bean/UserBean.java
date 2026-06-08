package com.example.prototype.bean;


import java.time.LocalDate;

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
public class UserBean {
	private String id;
	private String username;
	private String nameTH;
	private String surnameTH;
	private String nameEN;
	private String surnameEN;
	private String telephone;
	private String mobilePhone;
	private String idCard;
	private String email;
	private String gender;
	private LocalDate brithDate;
	private String roleNameTH;
	private String roleNameEN;
}
