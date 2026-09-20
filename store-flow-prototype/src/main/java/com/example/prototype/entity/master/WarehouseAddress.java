package com.example.prototype.entity.master;

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


@Getter @Setter
@Entity
@Table(name = "MS_WAREHOUSE_ADDRESS")
public class WarehouseAddress extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="code")
	private String code;
	
	@Column(name="house_no")
	private String houseNo;
	
	@Column(name="telephone")
	private String telephone;
	
	@Column(name="mobile_phone")
	private String mobilePhone;
	
	@ManyToOne
	@JoinColumn(name = "sub_district_code", referencedColumnName = "code")
	private SubDistricts subDistricts;
	
}
