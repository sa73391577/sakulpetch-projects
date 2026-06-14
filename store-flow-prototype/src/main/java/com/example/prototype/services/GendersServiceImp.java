package com.example.prototype.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prototype.entity.master.Genders;
import com.example.prototype.repository.jpa.GendersRepository;


@Service
public class GendersServiceImp implements GendersService {
	
	
	@Autowired private GendersRepository genderRepo;
	
	@Override
	public Genders getGenderByCode(String code) {
		return genderRepo.findByCode(code);
	}

}
