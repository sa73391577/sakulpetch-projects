package com.example.prototype.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.example.prototype.services.JwtServiceImp;
import com.example.prototype.services.UserDetailImp;
import com.example.prototype.services.UserInfoServiceImp;

import io.jsonwebtoken.Claims;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
	
	@Autowired @Lazy private UserDetailsService userDetailsService;
   
	@Autowired private JwtServiceImp jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("authorization");
        String requestHeader = request.getHeader("requester");
        String token = null;
        String username = null;
        
        //ถ้าไม่มี Token (เช่นจังหวะ Login) ให้ "ปล่อยผ่าน" ไปยัง Filter ถัดไปทันที
        if (authHeader == null || !authHeader.startsWith("Bearer ") || ("").equals(requestHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	logger.info("username : {}",username);
        	logger.info("token : {} ",token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) {
            	logger.info("Pass validateToken.");
            	Claims c = jwtService.extractAllClaims(token);
            	String registerId = c.getOrDefault("registerId", "").toString();
            	if(StringUtils.isNotBlank(registerId)) {
            		
                    if (userDetails instanceof UserDetailImp) {
                        ((UserDetailImp) userDetails).setRegisterId(registerId);
                    }
            		
            		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
            	}else {
            		logger.error(">>>> validate token : registerId is null.");
            	}
            }else {
            	logger.error(">>>> No Pass validateToken.");
            }
        }
        
        filterChain.doFilter(request, response);
    }

}
