package com.example.thaiaddressbatch.model.dto.internal;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProvinceCompositeDto {
	private ProvinceDto province;
	private List<DistrictDto> districts;
}
