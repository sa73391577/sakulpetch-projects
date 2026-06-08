package com.example.prototype.entity.transaction;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter
@Table(name = "USER_ROLES")
public class HistoryUserBuyProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="role_code")
	private String code;
	
	@Column(name="gender_name_th")
	private String nameTh;
	
	@Column(name="gender_name_en")
	private String nameEn;
	
}
