package com.example.prototype.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.prototype.Constants;
import com.example.prototype.bean.JwtTokenBean;
import com.example.prototype.bean.RespBean;
import com.example.prototype.bean.UserTokenBean;
import com.example.prototype.entity.auth.AuthRequest;
import com.example.prototype.exceptions.ErrorMessage;
import com.example.prototype.services.JwtServiceImp;

import io.micrometer.observation.annotation.Observed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Observed // เพิ่มบรรทัดนี้เพื่อให้ Micrometer เริ่มเก็บสถิติการเรียกใช้งาน
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private JwtServiceImp jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/check-controller-start")
	public String welcome() {
		logger.trace("A TRACE Message");
		logger.debug("A DEBUG Message");
		logger.info("An INFO Message");
		logger.warn("A WARN Message");
		logger.error("An ERROR Message");
		return "UserController Working !!!";
	}

	// Removed the role checks here as they are already managed in SecurityConfig
	@PostMapping("/login")
	public ResponseEntity<?> login(HttpServletRequest request, @RequestBody AuthRequest authRequest) {
		log.info("login request : " + ToStringBuilder.reflectionToString(authRequest));
		try {
			
			if( StringUtils.isBlank(authRequest.getUsername()) || StringUtils.isBlank(authRequest.getPassword())  ) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ErrorMessage(HttpStatus.BAD_REQUEST.value() 
								, null 
								, HttpStatus.BAD_REQUEST.name() 
								,"Request Parameter Invalid." ) );
			}
			
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			if (authentication.isAuthenticated()) {
				String token = jwtService.generateToken(authRequest.getUsername());
				logger.debug("token : {}",token);
				if(StringUtils.isNotBlank(token)) {
					JwtTokenBean tokenBean = new JwtTokenBean();
					tokenBean.setToken(token);
					RespBean resp = new RespBean();
					resp.setStatusCode(Constants.STATUS_HTTP_CODE.CODE_SUCCESS);
					resp.setMessage("SUCCESS");
					resp.setData(tokenBean);
					return ResponseEntity.ok().header("Custom-Header", "X-App-Version")
							.contentType(MediaType.APPLICATION_JSON).body(resp);
				}else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
							.body(new ErrorMessage(HttpStatus.UNAUTHORIZED.value() 
									, null 
									, HttpStatus.UNAUTHORIZED.name() 
									,"Username or Password are incorrect." ) );
				}
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(new ErrorMessage(HttpStatus.UNAUTHORIZED.value() 
								, null 
								, HttpStatus.UNAUTHORIZED.name() 
								,"Username or Password are incorrect." ) );
			}
		} catch (BadCredentialsException err) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorMessage(HttpStatus.UNAUTHORIZED.value() 
							, null 
							, HttpStatus.UNAUTHORIZED.name() 
							,"Username or Password are incorrect." ) );
		}
	}

}
