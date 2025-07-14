package com.api.wall.services;

import com.api.wall.models.User;
import com.api.wall.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
	@Autowired
	private IUserRepository userRepository;

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(int id) {
		return userRepository.findById(id).orElse(null);
	}

	private Integer findId(String userName) {
		return userRepository.findIdByUserName(userName);
	}

	@Override
	public Integer getIdByUserName(String userName) {
		return findId(userName);
	}

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User updateUser(User user, Integer id) {
		User userToUpdate = userRepository.findById(id).orElse(null);
		if (userToUpdate != null) {
			userToUpdate.setUserName(user.getUserName());
			userToUpdate.setEmail(user.getEmail());
			userToUpdate.setPasswd(user.getPasswd());
		}
		return userRepository.save(user);
	}

	@Override
	public boolean deleteUser(int id) {
		try {
			userRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
