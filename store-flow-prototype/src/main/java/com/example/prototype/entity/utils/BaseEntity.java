package com.example.prototype.entity.utils;

import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter
public class BaseEntity {
	
	@CreationTimestamp
	@Column(name="created_date")
	private LocalDate createdDate;
	
	@UpdateTimestamp
	@Column(name="updated_date")
	private LocalDate updatedDate;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="updated_by")
	private String updatedBy;
	
}
