package com.example.prototype.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductsDto {
	private String productNameTh;
	private String productNameEn;
	private String barCode;
	private String costPrice;
	private String sellPrice;
	private String expireDate;
	private String statusCode;
	private String statusNameTh;
	private String statusNameEn;
}
