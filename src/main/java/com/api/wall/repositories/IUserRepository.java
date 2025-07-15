package com.api.wall.repositories;

import com.api.wall.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserRepository extends JpaRepository<User, Integer> {

	@Query(value = "SELECT id FROM users WHERE user_name = :userName", nativeQuery = true)
	Integer findIdByUserName(@Param("userName") String userName);

	@Query(value = "SELECT passwd FROM users WHERE id = :id", nativeQuery = true)
	String findPasswdByUserId(@Param("id") Integer id);
}
