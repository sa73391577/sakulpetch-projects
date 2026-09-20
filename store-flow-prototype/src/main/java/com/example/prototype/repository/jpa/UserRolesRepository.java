package com.example.prototype.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.prototype.entity.master.Users;
import com.example.prototype.entity.transaction.UserRoles;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;

public interface UserRolesRepository extends JpaRepository<UserRoles, Integer> {
	
	void deleteByUser(Users u);
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true , value = "DELETE FROM USER_ROLES where role_code not in(:roleCodeList) and username = :usernmae")
	void deleteByRoleCodeNotInAndUsername(@Param("roleCode") List<String> roleCodeList , @Param("username") String usernmae);
	
}
