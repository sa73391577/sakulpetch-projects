package com.example.prototype.entity.master;

import java.util.List;
import com.example.prototype.entity.transaction.UserRoles;
import com.example.prototype.entity.utils.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
@Table(name = "MS_USERS")
public class Users extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="code")
	private String code;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@OneToMany(mappedBy = "user")
	private List<UserRoles> userRoles;
	
	@OneToMany(mappedBy = "user")
	private List<Profiles> profile;
	
}
