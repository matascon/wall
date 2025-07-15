package com.api.wall.services;

import com.api.wall.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUserService {
	public List<User> getUsers();

	public User getUserById(int id);

	public Integer getIdByUserName(String userName);

	public String getPasswdById(Integer id);

	public User createUser(User user);

	public User updateUser(User user, Integer id);

	public boolean deleteUser(int id);
}
