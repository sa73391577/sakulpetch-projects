package com.example.prototype.services;

import java.util.List;

import com.example.prototype.bean.RoleBean;
import com.example.prototype.bean.request.RoleFormRequestBean;

public interface RoleService {
	RoleBean getByCode(String code);
	List<RoleBean> list();
	void addRole(RoleFormRequestBean roleBean);
}
