package com.example.thaiaddressbatch.model.dto.external;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProvinceResponseDto {
	private int id;
	private String nameTh;
	private String nameEn;
	private List<DistrictResponseDto> districts;
}
