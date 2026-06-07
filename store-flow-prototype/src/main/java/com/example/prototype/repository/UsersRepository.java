package com.example.prototype.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.prototype.entity.master.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
	Optional<Users> findByUsername(String username); // check exist of data.
	List<Users> findAll();
	void deleteByUsername(String username);
}
