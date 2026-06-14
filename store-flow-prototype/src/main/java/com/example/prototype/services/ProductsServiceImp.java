package com.example.prototype.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.prototype.bean.request.ProductsFormRequest;
import com.example.prototype.dto.ProductsDto;
import com.example.prototype.repository.mapper.MapperProductsRepository;

@Service
public class ProductsServiceImp implements ProductsService {
	
	
	@Autowired private MapperProductsRepository mapperProductRepo;

	@Override
	public List<ProductsDto> findAll() {
		return mapperProductRepo.search(null);
	}

	@Override
	public List<ProductsDto> search(ProductsFormRequest prodReq) {
		return mapperProductRepo.search(prodReq);
	}
	
}
