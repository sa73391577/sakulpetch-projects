package com.example.prototype.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.prototype.bean.request.ProductsFormRequest;
import com.example.prototype.dto.ProductsDto;

@Mapper
public interface MapperProductsRepository {
	List<ProductsDto> search(ProductsFormRequest productReq);
}
