package com.example.prototype.entity.redis;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Getter;
import lombok.Setter;

@RedisHash(value = "user_profile")
@Getter @Setter
public class UserProfileRedis {
	
	@Id
	private String id;
	
	@Indexed
	private String username;
	
	@TimeToLive
	private Long timeoutInSeconds;
	
	private String gender;
	private String nameTH;
	private String surnameTH;
	private String nameEN;
	private String surnameEN;
	private String telephone;
	private String mobilePhone;
	private String email;
	private String idCard;
	private LocalDate brithDate;
	
}
