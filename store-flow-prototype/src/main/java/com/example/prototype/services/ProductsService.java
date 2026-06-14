package com.example.prototype.services;

import java.util.List;
import com.example.prototype.bean.request.ProductsFormRequest;
import com.example.prototype.dto.ProductsDto;

public interface ProductsService {
	List<ProductsDto> findAll();
	List<ProductsDto> search(ProductsFormRequest prodReq);
}
