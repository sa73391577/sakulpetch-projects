package com.example.prototype.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.prototype.entity.master.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
	Roles findByCode(String code);
}
