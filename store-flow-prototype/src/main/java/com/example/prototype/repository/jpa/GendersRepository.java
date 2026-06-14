package com.example.prototype.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.prototype.entity.master.Genders;

public interface GendersRepository extends JpaRepository<Genders, Integer> {
	Genders findByCode(String code);
}
