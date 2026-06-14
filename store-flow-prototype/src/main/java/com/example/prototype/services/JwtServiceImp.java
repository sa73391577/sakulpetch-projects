package com.example.prototype.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.prototype.bean.UserBean;
import com.example.prototype.entity.redis.UserProfileRedis;
import com.example.prototype.repository.jpa.UsersRepository;
import com.example.prototype.repository.redis.UserProfileRedisRepo;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtServiceImp {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtServiceImp.class);
	
	@Autowired private UserInfoService  userInfoService;
	@Autowired private UserProfileRedisRepo userProfileRedisRepo;
	public static final String SECRET = "5367566859703373367639792F423F452848284D6251655468576D5A71347437";
	

	public String generateToken(String username) { 
		Map<String, Object> claims = new HashMap<>();
		
		//get user info.
		UserBean userBean = userInfoService.getUserByUsername(username);
		
        //TODO: Save UUID in Redis.
        //Save uuid in Redis.
        UserProfileRedis up = userProfileRedisRepo.findByUsername(username);
        logger.debug(">>>> generateToken up : {}",up);
        if(null == up) {
        	 up = new UserProfileRedis();
        	 UUID uuidRandom = UUID.randomUUID();
             String uuid = uuidRandom.toString();
        	 up.setId(uuid);
        }
       
        up.setNameTH(userBean.getNameTH());
        up.setSurnameTH(userBean.getSurnameTH());
        up.setNameEN(userBean.getNameEN());
        up.setSurnameEN(userBean.getSurnameEN());
		up.setTelephone(userBean.getTelephone());
		up.setMobilePhone(userBean.getMobilePhone());
		up.setIdCard(userBean.getIdCard());
		up.setEmail(userBean.getEmail());
		up.setBrithDate(userBean.getBrithDate());
		up.setUsername(username);
		up.setTimeoutInSeconds(86400L); // Time to Live : 24 HR. unit is sec.
        userProfileRedisRepo.save(up);
		
		//Set Info in JWT Body.
		claims.put("registerId", up.getId());
		
		return createToken(claims, username);
	}

	private String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder().setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date())
				//Set Expiration. it is 30 min. 60 is second and 30 min.
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		System.out.println("userDetails validateToken() : "+ToStringBuilder.reflectionToString(userDetails));
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}