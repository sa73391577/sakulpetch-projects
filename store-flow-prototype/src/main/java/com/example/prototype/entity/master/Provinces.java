package com.example.prototype.entity.master;

import com.example.prototype.entity.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@Entity
@Table(name = "MS_PROVINCES")
public class Provinces extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="code")
	private String code;
	
	@Column(name="name_th")
	private String nameTh;
	
	@Column(name="name_en")
	private String nameEn;
	
}
