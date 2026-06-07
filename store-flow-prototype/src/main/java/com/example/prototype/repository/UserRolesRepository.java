package com.example.prototype.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.prototype.entity.master.Users;
import com.example.prototype.entity.transaction.UserRoles;

public interface UserRolesRepository extends JpaRepository<UserRoles, Integer> {
	void deleteByUser(Users u);
}
