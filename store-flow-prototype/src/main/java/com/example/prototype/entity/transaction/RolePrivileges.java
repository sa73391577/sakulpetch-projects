package com.example.prototype.entity.transaction;

import java.time.LocalDateTime;

import com.example.prototype.entity.master.Privileges;
import com.example.prototype.entity.master.Roles;
import com.example.prototype.entity.master.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter
@Table(name = "user_roles")
public class RolePrivileges {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="code")
	private String code;
	
	//privilege_code
	@ManyToOne
	@JoinColumn(name = "privilege_code", referencedColumnName = "code")
	private Privileges privilege;
	
	//role_code
	@ManyToOne
	@JoinColumn(name = "role_code", referencedColumnName = "code")
	private Roles role;
	
	@Column(name="is_actived")
	private Boolean isActived;
	
	
}
