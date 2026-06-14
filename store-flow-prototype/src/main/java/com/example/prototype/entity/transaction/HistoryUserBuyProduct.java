package com.example.prototype.entity.transaction;

import java.time.LocalDateTime;

import com.example.prototype.entity.master.Products;
import com.example.prototype.entity.master.Users;

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
@Table(name = "TR_HISTORY_USER_BUY_PRODUCTS")
public class HistoryUserBuyProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "user_code", referencedColumnName = "code")
	private Users user;
	
	@ManyToOne
	@JoinColumn(name="product_code" , referencedColumnName = "code")
	private Products product;
	
}
