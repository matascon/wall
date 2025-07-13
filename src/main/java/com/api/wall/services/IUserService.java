package com.api.wall.services;

import com.api.wall.models.User;

import java.util.List;

public interface IUserService {
	public List<User> getUsers();
	public User getUser(int id);
	public User createUser(User user);
	public User updateUser(User user, Integer id);
	public boolean deleteUser(int id);
}
