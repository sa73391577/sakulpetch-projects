package com.example.prototype.entity.transaction;

import java.time.LocalDateTime;
import java.util.Date;

import com.example.prototype.entity.master.ProductStatus;
import com.example.prototype.entity.master.Products;
import com.example.prototype.entity.master.Warehouses;
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
public class ProductsInWarehouse extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	// product_code
	@ManyToOne
	@JoinColumn(name = "product_code", referencedColumnName = "code")
	private Products product;
	
	// warhouse_code
	@ManyToOne
	@JoinColumn(name = "warhouse_code", referencedColumnName = "code")
	private Warehouses warehouses;
}
