package com.api.wall.controllers;

import com.api.wall.dto.DataLogin;
import com.api.wall.dto.DataValidateUser;
import com.api.wall.models.User;
import com.api.wall.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private IUserService userService;

	@GetMapping
	public List<User> getUsers() { return this.userService.getUsers(); }

	@GetMapping(path = "/{id}")
	public User getUser(@PathVariable Integer id) { return this.userService.getUserById(id); }

	//MAIN METHODS FOR WALL
	//	👇

	@PostMapping("/validateUser")
	public boolean validateUser(@RequestBody DataValidateUser request) {
		if (this.userService.getIdByUserName(request.userName()) != null) {
			return true;
		} else {
			return false;
		}
	}

	@PostMapping("/loginUser")
	public boolean loginUser(@RequestBody DataLogin request) {
		if (this.userService.getIdByUserName(request.userName()) != null) {
			if (this.userService.getPasswdById(this.userService.getIdByUserName(request.userName())).equals(request.passwd())) {
				return true;
			}
		}
		return false;
	}

	@PostMapping("/registerUser")
	public User createUser(@RequestBody User user) { return this.userService.createUser(user); }

	//	☝️
	//MAIN METHODS FOR WALL

	@PutMapping(path = "{id}")
	public User updateUser(@RequestBody User user, @PathVariable Integer id) {
		return this.userService.updateUser(user, id);
	}

	@DeleteMapping(path = "/{id}")
	public boolean deleteUser(@PathVariable Integer id) {
		if (this.userService.deleteUser(id)) {
			System.out.println("User " + id + " was deleted successfully");
			return true;
		} else {
			System.out.println("User " + id + " was not deleted");
			return false;
		}
	}
}
