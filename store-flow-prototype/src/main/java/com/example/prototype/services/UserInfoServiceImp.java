package com.example.prototype.services;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.prototype.Constants;
import com.example.prototype.bean.UserBean;
import com.example.prototype.bean.request.UserFormRequestBean;
import com.example.prototype.entity.master.Genders;
import com.example.prototype.entity.master.Profiles;
import com.example.prototype.entity.master.Roles;
import com.example.prototype.entity.master.Users;
import com.example.prototype.entity.redis.UserProfileRedis;
import com.example.prototype.entity.transaction.UserRoles;
import com.example.prototype.repository.jpa.ProfilesRepository;
import com.example.prototype.repository.jpa.RolesRepository;
import com.example.prototype.repository.jpa.UserRolesRepository;
import com.example.prototype.repository.jpa.UsersRepository;
import com.example.prototype.repository.redis.UserProfileRedisRepo;
import com.example.prototype.services.utils.DateUtils;
import com.example.prototype.services.utils.ValidationUtils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Service("UserInfoService_V1")
public class UserInfoServiceImp implements UserInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImp.class);
	
	@Autowired private UserProfileRedisRepo userProfileRedisRepo;
	@Autowired @Lazy private UsersRepository userInfoRepo;
	@Autowired private RolesRepository rolesRepo;
	@Autowired private UserRolesRepository userRolesRepo;
	@Autowired private ProfilesRepository profilesRepo;
	@Autowired private PasswordEncoder encoder;
	@Autowired private GendersService gendersService;

    // Add any additional methods for registering or managing users
    @Transactional
    @Override
    public void addUser(UserFormRequestBean userInfo) throws BadRequestException {
    	
    	if(StringUtils.isBlank(userInfo.getUsername()) 
    			|| StringUtils.isBlank(userInfo.getNameTH()) 
    			|| StringUtils.isBlank(userInfo.getSurnameTH()) 
    			|| StringUtils.isBlank(userInfo.getNameEN()) 
    			|| StringUtils.isBlank(userInfo.getSurnameEN()) 
    			|| StringUtils.isBlank(userInfo.getTelephone()) 
    			|| StringUtils.isBlank(userInfo.getMobilePhone()) 
    			|| StringUtils.isBlank(userInfo.getIdCard()) 
    			|| StringUtils.isBlank(userInfo.getEmail()) 
    			|| StringUtils.isBlank(userInfo.getGenderCode()) 
    			|| StringUtils.isBlank(userInfo.getPassword())
    			|| StringUtils.isBlank(userInfo.getCreatedBy())) {
    		logger.debug(">>>>> parameter is bad request.");
    		throw new BadRequestException();
    	}
    	
    	//validate fomate input data.
    	Genders g = gendersService.getGenderByCode(userInfo.getGenderCode());
    	if(null==g) {throw new EntityNotFoundException("gender code not found in database : "+userInfo.getGenderCode());}
    	
    	if(ValidationUtils.isValidEmail(userInfo.getEmail())) {
    		throw new IllegalArgumentException("Invalid email format: "+userInfo.getEmail());
    	}
    	
    	if(ValidationUtils.isValidIdCard(userInfo.getIdCard())) {
    		throw new IllegalArgumentException("Invalid id card format: "+userInfo.getIdCard());
    	}
    	
    	Optional<Users> userCreatedOption = userInfoRepo.findByUsername(userInfo.getCreatedBy());
    	Users userCreatedBy = userCreatedOption.orElseThrow(() -> { return new EntityNotFoundException("user not found in database : "+userInfo.getCreatedBy()); } );
    	
    	
    	//UserRoles ur = new UserRoles();
    	//ur = userRolesRepo.save(ur);
    	
    	LocalDate currentLocalDate = LocalDate.now();
    	Users u = new Users();
    	u.setUsername(userInfo.getUsername());
    	u.setPassword(encoder.encode(userInfo.getPassword()));
    	u.setCreatedBy(userCreatedBy.getUsername());
    	u.setUpdatedBy(userCreatedBy.getUsername());
    	u.setCreatedDate(currentLocalDate);
    	u.setUpdatedDate(currentLocalDate);
    	u = userInfoRepo.save(u);
    	u.setCode(String.format(Constants.FORMATE_CODE_TABLE.USER_TB, u.getId()));
    	u = userInfoRepo.saveAndFlush(u);
    	
    	Profiles p = new Profiles();
    	p.setUser(u);
    	p.setNameTh(userInfo.getNameTH());
    	p.setSurnameTh(userInfo.getSurnameTH());
    	p.setNameEn(userInfo.getNameEN());
    	p.setSurnameEn(userInfo.getSurnameEN());
    	p.setTelephone(userInfo.getTelephone());
    	p.setMobilePhone(userInfo.getMobilePhone());
    	p.setIdCard(userInfo.getIdCard());
    	p.setEmail(userInfo.getEmail());
    	p.setGender(g);
    	p.setIsActived(Constants.STATUS_PROFILE.PROFILE_IS_ACTIVED);
    	p.setBrithDate(DateUtils.convertStringToLocalDate(userInfo.getBrithDate()));
    	p.setCreatedBy(userCreatedBy.getUsername());
    	p.setUpdatedBy(userCreatedBy.getUsername());
    	p.setCreatedDate(currentLocalDate);
    	p.setUpdatedDate(currentLocalDate);
    	profilesRepo.save(p);
    	
    	for(String code : userInfo.getRoleCode()) {
    		Roles r = rolesRepo.findByCode(code);
    		if(null!=r) {
    			UserRoles ur = new UserRoles();
    			ur.setUser(u);
    			ur.setRole(r);
    			ur.setIsActing(false);
    			ur.setIsActived(true);
    			ur.setCreatedBy(userCreatedBy.getUsername());
    			ur.setUpdatedBy(userCreatedBy.getUsername());
    			ur.setCreatedDate(currentLocalDate);
    			ur.setUpdatedDate(currentLocalDate);
    			ur = userRolesRepo.save(ur);
    			ur.setCode(String.format(Constants.FORMATE_CODE_TABLE.USER_TB, ur.getId()));
    			userRolesRepo.save(ur);
    		}
    	}
    }
    
    // Add any additional methods for registering or managing users
    @Transactional
    public boolean delUserByUsername(String username) {
		Optional<Users> u = userInfoRepo.findByUsername(username);
		logger.debug("u deleted : {} " , u);
		logger.debug("u.isPresent() deleted : {} " , u.isPresent());
		if(u.isPresent()) {
			userRolesRepo.deleteByUser(u.get());
			userRolesRepo.flush();
    		userInfoRepo.deleteByUsername(username);
    		userInfoRepo.flush();
            return true;
		}
    	return false;
    }
    
    @Transactional
    public List<UserBean> getAllUser() {
    	logger.debug("getAllUser working !!!");
    	try {
    		return userInfoRepo.findAll().stream().map(source -> {			
    			UserBean u = new UserBean();
    			u.setId(String.valueOf(source.getId()));
    			u.setUsername(u.getUsername());
    			source.getProfile().forEach(p -> {
    				if( Constants.STATUS_PROFILE.PROFILE_IS_ACTIVED.equalsIgnoreCase(p.getIsActived())  ) {
    					u.setNameTH(p.getNameTh());
    					u.setSurnameTH(p.getSurnameTh());
    					u.setNameEN(p.getNameEn());
    					u.setSurnameEN(p.getSurnameEn());
    					u.setTelephone(p.getTelephone());
    					u.setMobilePhone(p.getMobilePhone());
    					u.setIdCard(p.getIdCard());
    					u.setEmail(p.getEmail());
    					u.setBrithDate(p.getBrithDate());
    				}
    			});
    			if(null!=source.getUserRoles() && source.getUserRoles().size() > 0) {
    				List<String> roleNameTHList = new ArrayList<String>();
    				List<String> roleNameENList = new ArrayList<String>();
    				source.getUserRoles().forEach(ur->{
    					roleNameTHList.add(ur.getRole().getNameTh());
    					roleNameENList.add(ur.getRole().getNameEn());
    				});
    				u.setRoleNameTH(roleNameTHList.toString());
    				u.setRoleNameEN(roleNameENList.toString());
    			}
    			return u;
    		}).collect(Collectors.toList());
    	}catch(Exception err) {
    		logger.error("getAllUser Error please check !!! err : {}",err);
    	}
    	return null;
    }

	@Override
	public UserBean getUserByUsername(String username) {
		Optional<Users> userOption = userInfoRepo.findByUsername(username);
		Users user = userOption.orElseThrow(()->{ return new RuntimeException("Not found username: " + username); });
		UserBean userBean = new UserBean();
		user.getProfile().forEach(p -> {
			if( Constants.STATUS_PROFILE.PROFILE_IS_ACTIVED.equalsIgnoreCase(p.getIsActived())  ) {
				userBean.setNameTH(p.getNameTh());
				userBean.setSurnameTH(p.getSurnameTh());
				userBean.setNameEN(p.getNameEn());
				userBean.setSurnameEN(p.getSurnameEn());
				userBean.setTelephone(p.getTelephone());
				userBean.setMobilePhone(p.getMobilePhone());
				userBean.setIdCard(p.getIdCard());
				userBean.setEmail(p.getEmail());
				userBean.setBrithDate(p.getBrithDate());
			}
		});
		if(null!=user.getUserRoles() && user.getUserRoles().size() > 0) {
			List<String> roleNameTHList = new ArrayList<String>();
			List<String> roleNameENList = new ArrayList<String>();
			user.getUserRoles().forEach(ur->{
				roleNameTHList.add(ur.getRole().getNameTh());
				roleNameENList.add(ur.getRole().getNameEn());
			});
			userBean.setRoleNameTH(roleNameTHList.toString());
			userBean.setRoleNameEN(roleNameENList.toString());
		}
		return userBean;
	}

	@Override
	public UserBean getUserProfileOfUserLoginByUsername(String username) {
		// TODO Auto-generated method stub
		UserProfileRedis upr  = userProfileRedisRepo.findByUsername(username);
		logger.info("upr : {}",ToStringBuilder.reflectionToString(upr));
		if(null!=upr) {
			UserBean userBean = new UserBean();
			userBean.setNameTH(upr.getNameTH());
			userBean.setSurnameTH(upr.getSurnameTH());
			userBean.setNameEN(upr.getNameEN());
			userBean.setSurnameEN(upr.getSurnameEN());
			userBean.setTelephone(upr.getTelephone());
			userBean.setMobilePhone(upr.getMobilePhone());
			userBean.setIdCard(upr.getIdCard());
			userBean.setEmail(upr.getEmail());
			userBean.setBrithDate(upr.getBrithDate());
			userBean.setGender(upr.getGender());
			return userBean;
		}else {
			return getUserByUsername(username);
		}
	}
    
    
}