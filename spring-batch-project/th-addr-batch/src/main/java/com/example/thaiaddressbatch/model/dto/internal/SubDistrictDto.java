package com.example.thaiaddressbatch.model.dto.internal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SubDistrictDto {
	
	public SubDistrictDto() {
		
	}
	
	public SubDistrictDto(String code , String nameTh , String nameEn,String zipCode) {
		this.code = code;
		this.nameTh = nameTh;
		this.nameEn = nameEn;
		this.zipCode = zipCode;
	}
	
	private int id;
	private String code;
	private String nameTh;
	private String nameEn;
	private String zipCode;
}