package com.example.prototype.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.prototype.entity.master.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailImp implements UserDetails {
	
	private static final long serialVersionUID = 1L;

    private int id;

    private String username;

    private String registerId;

    @JsonIgnore
    private String password;
    
    private Collection<? extends GrantedAuthority> authorities;
	
	public UserDetailImp(Users user , Collection<? extends GrantedAuthority> authorities) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = authorities;
    }
	
	public static UserDetailImp build(Users user) {
		List<String> roleDummy = new ArrayList<String>();
		
		if(null!=user && null!=user.getUserRoles() && user.getUserRoles().size() > 0) {
			user.getUserRoles().forEach(ur->{
				roleDummy.add(ur.getRole().getNameEn().toUpperCase());
			});
		}
		
		List<GrantedAuthority> authorities = roleDummy.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
		
        return new UserDetailImp(
                user,
                authorities
        );
    }
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public @Nullable String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}



	public Integer getId() {
		return this.id;
	}



	public void setId(Integer id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	
	
}
