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
import com.example.prototype.bean.RoleBean;
import com.example.prototype.bean.UserBean;
import com.example.prototype.bean.request.GetUserFormRequestBean;
import com.example.prototype.bean.request.RoleFormRequestBean;
import com.example.prototype.bean.request.UserFormRequestBean;
import com.example.prototype.entity.master.Users;
import com.example.prototype.services.RoleService;
import com.example.prototype.services.UserDetailImp;
import com.example.prototype.services.UserInfoService;

import io.micrometer.observation.annotation.Observed;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Observed // เพิ่มบรรทัดนี้เพื่อให้ Micrometer เริ่มเก็บสถิติการเรียกใช้งาน
@RestController
@RequestMapping("/role")
public class RoleController {

	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Autowired private RoleService roleService;
	
	// C : Created
	@PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespBean> addNewUser(HttpServletRequest request
			, @AuthenticationPrincipal UserDetailImp userDetails
			, @RequestBody RoleFormRequestBean roleFormBean) {
		logger.info("new roleBean : {}",ToStringBuilder.reflectionToString(roleFormBean));
		try {
			if(StringUtils.isNotBlank(userDetails.getUsername())) {
				roleFormBean.setCreatedBy(userDetails.getUsername());
				roleService.addRole(roleFormBean);
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
		return ResponseEntity.status(HttpStatus.OK).header("Custom-Header", "X-App-Version")
				.body(new RespBean(Constants.STATUS_HTTP_CODE.CODE_CREATED
				, Constants.STATUS_HTTP_MESSAGES.CREATED
				, null));
	}
	
	//R : Read.
	@PostMapping(value = "/get/code", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespBean> getUserProfileOfUserLogin(HttpServletRequest request, @RequestBody RoleFormRequestBean roleFormBean) {
		logger.info("getUserProfile working !!! reqForm : {}",ToStringBuilder.reflectionToString(roleFormBean));
		
		//validate role name TH & EN.
		if( StringUtils.isBlank(roleFormBean.getRoleCode()) ) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new RespBean(Constants.STATUS_HTTP_CODE.CODE_BAD_REQUEST 
							, Constants.STATUS_HTTP_MESSAGES.BAD_REQUEST
							, null));
		}
		
		RoleBean rb = roleService.getByCode(roleFormBean.getRoleCode());
		if(null == rb) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespBean(Constants.STATUS_HTTP_CODE.CODE_NOT_FOUND 
					, Constants.STATUS_HTTP_MESSAGES.NOT_FOUND
					, new RespBean(Constants.STATUS_HTTP_CODE.CODE_NOT_FOUND 
							, Constants.STATUS_HTTP_MESSAGES.NOT_FOUND 
							, Constants.ERROR_MESSAGES.USER_NOT_FOUND)));
		}
		return ResponseEntity.status(HttpStatus.OK).header("Custom-Header", "X-App-Version").contentType(MediaType.APPLICATION_JSON)
				.body(new RespBean(Constants.STATUS_HTTP_CODE.CODE_SUCCESS , Constants.STATUS_HTTP_MESSAGES.SUCCESS , rb));
	}

}
