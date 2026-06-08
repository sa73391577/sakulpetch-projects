package com.example.prototype.services;

import com.example.prototype.bean.RoleBean;
import com.example.prototype.bean.request.RoleFormRequestBean;

public interface RoleService {
	RoleBean getByCode(String code);
	void addRole(RoleFormRequestBean roleBean);
}
