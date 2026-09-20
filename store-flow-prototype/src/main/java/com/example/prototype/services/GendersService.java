package com.example.prototype.services;

import java.util.List;

import com.example.prototype.bean.GendersBean;
import com.example.prototype.entity.master.Genders;

public interface GendersService {
	Genders getGenderByCode(String code);
	List<GendersBean> list();
}
