package com.example.prototype.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.prototype.bean.GendersBean;
import com.example.prototype.bean.RespBean;
import com.example.prototype.bean.RoleBean;
import com.example.prototype.bean.UserBean;
import com.example.prototype.bean.request.GetUserFormRequestBean;
import com.example.prototype.bean.request.RoleFormRequestBean;
import com.example.prototype.bean.request.UserFormRequestBean;
import com.example.prototype.constants.Constants;
import com.example.prototype.entity.master.Users;
import com.example.prototype.services.GendersService;
import com.example.prototype.services.UserDetailImp;
import com.example.prototype.services.UserInfoService;

import io.micrometer.observation.annotation.Observed;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Observed
@RestController
@RequestMapping("/gender")
public class GenderController {

	@Autowired private GendersService genderService;
	
	@GetMapping(value="/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RespBean> list(HttpServletRequest request) {
		log.info("list of Genders Working !!!");
		
		List<GendersBean> genList = genderService.list();
		
		if(null == genList) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespBean(Constants.STATUS_HTTP_CODE.CODE_NOT_FOUND 
					, Constants.STATUS_HTTP_MESSAGES.NOT_FOUND
					, new RespBean(Constants.STATUS_HTTP_CODE.CODE_NOT_FOUND 
							, Constants.STATUS_HTTP_MESSAGES.NOT_FOUND 
							, Constants.ERROR_MESSAGES.USER_NOT_FOUND)));
		}
		
		return ResponseEntity.status(HttpStatus.OK).header("Custom-Header", "X-App-Version").contentType(MediaType.APPLICATION_JSON)
				.body(new RespBean(Constants.STATUS_HTTP_CODE.CODE_SUCCESS , Constants.STATUS_HTTP_MESSAGES.SUCCESS , genList));
	}
	

}
