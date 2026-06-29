package com.example.thaiaddressbatch.model.dto.external;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SubDistrictResponseDto {
	private int id;
	private String nameTh;
	private String nameEn;
	private List<SubDistrictsInfo> subdistricts;
	
	@Getter @Setter
	public static class SubDistrictsInfo {
		private int id;
		private String nameTh;
		private String nameEn;
		private String zipCode;
	}
}