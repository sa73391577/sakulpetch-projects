package com.example.prototype.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.prototype.Constants;
import com.example.prototype.bean.RespBean;
import com.example.prototype.bean.UserBean;
import com.example.prototype.bean.request.GetUserFormRequestBean;
import com.example.prototype.bean.request.UserFormRequestBean;
import com.example.prototype.entity.master.Users;
import com.example.prototype.services.UserDetailImp;
import com.example.prototype.services.UserInfoService;

import io.micrometer.observation.annotation.Observed;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Observed // เพิ่มบรรทัดนี้เพื่อให้ Micrometer เริ่มเก็บสถิติการเรียกใช้งาน
@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	@Qualifier("UserInfoService_V1")
	private UserInfoService userInfoService;
	
	// C : Created
	@PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespBean> addNewUser(HttpServletRequest request
			, @AuthenticationPrincipal UserDetailImp userDetails
			, @RequestBody UserFormRequestBean userInfo) {
		logger.info("userInfo : {}",ToStringBuilder.reflectionToString(userInfo));
		logger.info("userDetails.getAuthorities() : {}",userDetails.getAuthorities().toString());
		logger.info("userDetails.getRegisterId() : {}",userDetails.getRegisterId());
		
		try {
			logger.info("add new user working !!! userInfo : {}",ToStringBuilder.reflectionToString(userInfo));
			if(StringUtils.isNotBlank(userDetails.getUsername())) {
				userInfo.setCreatedBy(userDetails.getUsername());
				userInfoService.addUser(userInfo);
			}else {
				throw new BadRequestException();
			}
		}
		catch(BadRequestException eBad) {
			logger.error("BadRequestException error : {}",eBad.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new RespBean(Constants.STATUS_HTTP_CODE.CODE_BAD_REQUEST 
							, Constants.STATUS_HTTP_MESSAGES.BAD_REQUEST
							, null) );
		}
		catch(EntityNotFoundException eNotFound) {
			logger.error("EntityNotFoundException error : {}",eNotFound.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new RespBean(Constants.STATUS_HTTP_CODE.CODE_NOT_FOUND 
							, Constants.STATUS_HTTP_MESSAGES.NOT_FOUND
							, Constants.ERROR_MESSAGES.USER_CREATED_BY_NOT_FOUND) );
		}
		catch(IllegalArgumentException eFormat) {
			logger.error("IllegalArgumentException error : {}",eFormat.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new RespBean(Constants.STATUS_HTTP_CODE.CODE_BAD_REQUEST 
							, Constants.STATUS_HTTP_MESSAGES.BAD_REQUEST
							, null) );
		}
		return ResponseEntity.status(HttpStatus.OK).header("Custom-Header", "X-App-Version").body(new RespBean(Constants.STATUS_HTTP_CODE.CODE_CREATED
				, Constants.STATUS_HTTP_MESSAGES.CREATED
				, null));
	}
	
	// R : Read
	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespBean> getUserList(HttpServletRequest request) {
		logger.info("getUserList working !!! request : {}",request);
		List<UserBean> reslist = userInfoService.getAllUser();
		if (null != reslist && reslist.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).header("Custom-Header", "X-App-Version").contentType(MediaType.APPLICATION_JSON)
					.body(new RespBean(Constants.STATUS_HTTP_CODE.CODE_SUCCESS , Constants.STATUS_HTTP_MESSAGES.SUCCESS , reslist));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespBean(Constants.STATUS_HTTP_CODE.CODE_NOT_FOUND 
					, Constants.STATUS_HTTP_MESSAGES.NOT_FOUND
					, null));
		}
	}
	
	//R : Read.
	@PostMapping(value = "/user-profile/user-login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespBean> getUserProfileOfUserLogin(HttpServletRequest request, @RequestBody GetUserFormRequestBean reqForm) {
		logger.info("getUserProfile working !!! reqForm : {}",ToStringBuilder.reflectionToString(reqForm));
		
		//validate username.
		if(StringUtils.isBlank(reqForm.getUsername())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new RespBean(Constants.STATUS_HTTP_CODE.CODE_BAD_REQUEST 
							, Constants.STATUS_HTTP_MESSAGES.BAD_REQUEST
							, null));
		}
		
		UserBean ub = userInfoService.getUserProfileOfUserLoginByUsername(reqForm.getUsername());
		if(null == ub) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespBean(Constants.STATUS_HTTP_CODE.CODE_NOT_FOUND 
					, Constants.STATUS_HTTP_MESSAGES.NOT_FOUND
					, new RespBean(Constants.STATUS_HTTP_CODE.CODE_NOT_FOUND 
							, Constants.STATUS_HTTP_MESSAGES.NOT_FOUND 
							, Constants.ERROR_MESSAGES.USER_NOT_FOUND)));
		}
		return ResponseEntity.status(HttpStatus.OK).header("Custom-Header", "X-App-Version").contentType(MediaType.APPLICATION_JSON)
				.body(new RespBean(Constants.STATUS_HTTP_CODE.CODE_SUCCESS , Constants.STATUS_HTTP_MESSAGES.SUCCESS , ub));
	}
	
	// D : Delete
	@DeleteMapping("/delete")
	public ResponseEntity<RespBean> delUser(HttpServletRequest request, @RequestBody GetUserFormRequestBean userInfo) {
		logger.info("userInfo : " + ToStringBuilder.reflectionToString(userInfo));
		try {
			boolean isDelSuccess = userInfoService.delUserByUsername(userInfo.getUsername());
			if(isDelSuccess) {
				return ResponseEntity.status(HttpStatus.OK).header("Custom-Header", "X-App-Version").body(
						new RespBean(Constants.STATUS_HTTP_CODE.CODE_NO_CONTENT
						,Constants.STATUS_HTTP_MESSAGES.NO_CONTENT 
						, null));
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Custom-Header", "X-App-Version").body(
						new RespBean(Constants.STATUS_HTTP_CODE.CODE_NOT_FOUND
						,Constants.STATUS_HTTP_MESSAGES.NOT_FOUND 
						, Constants.ERROR_MESSAGES.USER_DELETED_NOT_FOUND));
			}
		}
		catch(Exception e) {
			logger.error("error : {}",e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Custom-Header", "X-App-Version").body(
					new RespBean(Constants.STATUS_HTTP_CODE.CODE_INTERNAL_SERVICE_ERROR
					,Constants.STATUS_HTTP_MESSAGES.INTERNAL_SERVICE_ERROR 
					, null));
		}
	}

}
