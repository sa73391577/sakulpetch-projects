package com.example.prototype.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.prototype.entity.master.Profiles;

public interface ProfilesRepository extends JpaRepository<Profiles, Integer> {
	
}
