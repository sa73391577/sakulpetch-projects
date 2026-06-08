package com.example.prototype.entity.transaction;

import com.example.prototype.entity.master.Roles;
import com.example.prototype.entity.master.Users;
import com.example.prototype.entity.utils.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_roles")
public class UserRoles extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "code")
	private String code;

	@Column(name = "is_actived")
	private Boolean isActived;
	
	@Column(name = "is_acting")
	private Boolean isActing;
	
	// user_code
	@ManyToOne
	@JoinColumn(name = "user_code", referencedColumnName = "code")
	private Users user;
	
	// role_code
	@ManyToOne
	@JoinColumn(name = "role_code", referencedColumnName = "code")
	private Roles role;

}
