package com.ykb.ATMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ykb.ATMS.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	@Query("Select s from User s where s.username=:username")
	public User findByUsername(String username);
}
