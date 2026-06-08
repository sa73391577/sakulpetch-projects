package com.example.prototype.repository.redis;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.prototype.entity.redis.UserProfileRedis;

@Repository
public interface UserProfileRedisRepo extends CrudRepository<UserProfileRedis ,String>  {
	Optional<UserProfileRedis> findById(String username);
	UserProfileRedis findByUsername(String username);
}
