package com.example.thaiaddressbatch.model.dto.external;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DistrictResponseDto {
	private int id;
	private String nameTh;
	private String nameEn;
	private List<DistrictsInfo> districts;
	
	@Getter @Setter
	public static class DistrictsInfo {
		private int id;
		private String nameTh;
		private String nameEn;
	}
	
}

