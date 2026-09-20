package com.example.prototype.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prototype.bean.GendersBean;
import com.example.prototype.entity.master.Genders;
import com.example.prototype.repository.jpa.GendersRepository;


@Service
public class GendersServiceImp implements GendersService {
	
	
	@Autowired private GendersRepository genderRepo;
	
	@Override
	public Genders getGenderByCode(String code) {
		return genderRepo.findByCode(code);
	}

	@Override
	public List<GendersBean> list() {
		List<Genders> list = genderRepo.findAll();
		
		if(null!=list) {
			return list.stream().map(item->{
				GendersBean bean = new GendersBean();
				bean.setCode(item.getCode());
				bean.setNameTH(item.getNameTh());
				bean.setNameEN(item.getNameEn());
				return bean;
			}).collect(Collectors.toList());
		}
		return null;
	}

}
