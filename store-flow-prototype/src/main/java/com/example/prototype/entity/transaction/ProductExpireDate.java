package com.example.prototype.entity.transaction;

import java.time.LocalDateTime;
import java.util.Date;

import com.example.prototype.entity.master.ProductStatus;
import com.example.prototype.entity.master.Products;
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
@Table(name = "TR_PRODUCT_EXPIRE_DATE")
public class ProductExpireDate extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "expired_date")
	private Date expiredDate;
	
	
	@Column(name = "cost_price")
	private String costPrice;
	
	@Column(name = "sell_price")
	private String sellPrice;
	

	// product_code
	@ManyToOne
	@JoinColumn(name = "product_code", referencedColumnName = "code")
	private Products product;
	
	// product_status_code
	@ManyToOne
	@JoinColumn(name = "product_status_code", referencedColumnName = "code")
	private ProductStatus productStatus;
}
