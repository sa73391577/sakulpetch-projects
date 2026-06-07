package com.example.prototype.entity.master;

import java.time.LocalDate;

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
@Getter @Setter
@Table(name = "MS_PROFILES")
public class Profiles extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "user_code", referencedColumnName = "code")
	private Users user;
	
	@ManyToOne
	@JoinColumn(name = "gender_code", referencedColumnName = "code")
	private Genders gender;
	
	@Column(name="name_th")
	private String nameTh;
	
	@Column(name="surname_th")
	private String surnameTh;
	
	@Column(name="name_en")
	private String nameEn;
	
	@Column(name="surname_en")
	private String surnameEn;
	
	
	@Column(name="telephone")
	private String telephone;
	
	@Column(name="mobile_phone")
	private String mobilePhone;
	
	@Column(name="email")
	private String email;
	
	@Column(name="id_card")
	private String idCard;
	
	@Column(name="brith_date")
	private LocalDate brithDate;
	
	@Column(name="is_actived")
	private String isActived;
	
}
