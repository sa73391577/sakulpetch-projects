package com.example.thaiaddressbatch.model.dto.internal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProvinceDto {
	
	public ProvinceDto() {
		
	}
	
	public ProvinceDto(String code ,String nameTh ,String nameEn) {
		this.code = code;
		this.nameTh = nameTh;
		this.nameEn = nameEn;
	}
	
	private int id;
	private String code;
	private String nameTh;
	private String nameEn;
}
