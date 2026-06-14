package com.example.prototype.bean.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductsFormRequest {
	private String searchNameTh;
	private String searchNameEn;
    private String searchBarCode;
    private Integer searchMinCostPrice;
    private Integer searchMaxCostPrice;
    private Integer searchMinSellPrice;
    private Integer searchMaxSellPrice;
    private LocalDate searchStartDate;
    private LocalDate searchEndDate;
    private String searchStatusCode; 
}
