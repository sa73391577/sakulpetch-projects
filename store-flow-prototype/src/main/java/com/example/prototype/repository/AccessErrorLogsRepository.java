package com.example.prototype.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.prototype.entity.log.AccessErrorLogs;

public interface AccessErrorLogsRepository extends JpaRepository<AccessErrorLogs, Integer> {
	
}
