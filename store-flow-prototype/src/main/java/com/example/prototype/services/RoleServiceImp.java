package com.example.prototype.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prototype.Constants;
import com.example.prototype.bean.RoleBean;
import com.example.prototype.bean.request.RoleFormRequestBean;
import com.example.prototype.entity.master.Roles;
import com.example.prototype.repository.RolesRepository;

@Service
public class RoleServiceImp implements RoleService {
	
	@Autowired private RolesRepository rolesRepo;

	@Override
	public RoleBean getByCode(String code) {
		Roles r = rolesRepo.findByCode(code);
		if(null!=r) {
			RoleBean rb = new RoleBean();
			rb.setCode(r.getCode());
			rb.setNameTH(r.getNameTh());
			rb.setNameEN(r.getNameEn());
			return rb;
		}
		return null;
	}

	@Override
	public void addRole(RoleFormRequestBean roleBean) {
		LocalDate currentLocalDate = LocalDate.now();
		Roles r = new Roles();
		r.setNameTh(roleBean.getRoleNameTH());
		r.setNameEn(roleBean.getRoleNameEN());
		r.setCreatedBy(roleBean.getCreatedBy());
		r.setUpdatedBy(roleBean.getCreatedBy());
		r.setCreatedDate(currentLocalDate);
		r.setUpdatedDate(currentLocalDate);
		r = rolesRepo.save(r);
		r.setCode(String.format(Constants.FORMATE_CODE_TABLE.ROLES_TB, r.getId()));
		rolesRepo.save(r);
	}
	
}
