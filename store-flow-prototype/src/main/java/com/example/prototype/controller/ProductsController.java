package com.example.prototype.controller;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.prototype.Constants;
import com.example.prototype.bean.RespBean;
import com.example.prototype.bean.request.ProductsFormRequest;
import com.example.prototype.dto.ProductsDto;
import com.example.prototype.services.ProductsService;

import io.micrometer.observation.annotation.Observed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Observed
@RestController
@RequestMapping("/products")
public class ProductsController {
	
	@Autowired private ProductsService productsService;
	
	@GetMapping(value="")
	public ResponseEntity<RespBean> findAll(HttpServletRequest request){
		log.debug("findAll() working !!!");
		List<ProductsDto> reslist = productsService.findAll();
		if(null!=reslist && reslist.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).header("Custom-Header", "X-App-Version").contentType(MediaType.APPLICATION_JSON)
					.body(new RespBean(Constants.STATUS_HTTP_CODE.CODE_SUCCESS , Constants.STATUS_HTTP_MESSAGES.SUCCESS , reslist));
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespBean(Constants.STATUS_HTTP_CODE.CODE_NOT_FOUND 
					, Constants.STATUS_HTTP_MESSAGES.NOT_FOUND
					, null));
		}
	}
	
	@PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespBean> search(HttpServletRequest request, @RequestBody ProductsFormRequest reqForm) {
		log.info("search working !!! reqForm : {}",ToStringBuilder.reflectionToString(reqForm));
		List<ProductsDto> reslist = productsService.search(reqForm);
		if(null!=reslist && reslist.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).header("Custom-Header", "X-App-Version").contentType(MediaType.APPLICATION_JSON)
					.body(new RespBean(Constants.STATUS_HTTP_CODE.CODE_SUCCESS , Constants.STATUS_HTTP_MESSAGES.SUCCESS , reslist));
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespBean(Constants.STATUS_HTTP_CODE.CODE_NOT_FOUND 
					, Constants.STATUS_HTTP_MESSAGES.NOT_FOUND
					, null));
		}
	}
	
	
}
