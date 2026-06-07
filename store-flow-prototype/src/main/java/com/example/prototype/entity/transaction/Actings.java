package com.example.prototype.entity.transaction;

import java.time.LocalDateTime;

import com.example.prototype.entity.utils.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ACTING")
public class Actings extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "code")
	private String code;

	@Column(name = "is_actived")
	private Boolean isActived;

	@Column(name = "actived_date")
	private LocalDateTime activedDate;

	@Column(name = "expired_date")
	private LocalDateTime expiredDate;

	// user_role_code
	@ManyToOne
	@JoinColumn(name = "user_role_code", referencedColumnName = "code")
	private UserRoles userRoles;
}
