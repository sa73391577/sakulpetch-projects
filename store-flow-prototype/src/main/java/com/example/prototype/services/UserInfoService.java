package com.example.prototype.services;

import java.util.List;
import org.apache.coyote.BadRequestException;
import com.example.prototype.bean.UserBean;
import com.example.prototype.bean.request.UserFormRequestBean;

public interface UserInfoService {
	void addUser(UserFormRequestBean userInfo) throws BadRequestException;
	boolean delUserByUsername(String username);
	UserBean getUserByUsername(String username);
	List<UserBean> getAllUser();
	UserBean getUserProfileOfUserLoginByUsername(String username);
}
