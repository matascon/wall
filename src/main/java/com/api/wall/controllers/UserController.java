package com.api.wall.controllers;

import com.api.wall.dto.LoginUserDTO;
import com.api.wall.dto.ValidateUserDTO;
import com.api.wall.models.User;
import com.api.wall.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
	private final IUserService userService;

	public UserController(IUserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<User> getUsers() { return this.userService.getUsers(); }

	@GetMapping(path = "/{id}")
	public User getUser(@PathVariable Integer id) { return this.userService.getUserById(id); }

	//MAIN METHODS FOR WALL
	//	👇

	@PostMapping("/validateUser")
	public ValidateUserDTO validateUser(@RequestBody ValidateUserDTO request) {
		if (this.userService.getIdByUserName(request.userName()) != null) {
			return request;
		}
		return new ValidateUserDTO(null);
	}

	@PostMapping("/loginUser")
	public LoginUserDTO loginUser(@RequestBody LoginUserDTO request) {
		if (this.userService.getIdByUserName(request.userName()) != null) {
			if (this.userService.getPasswdById(this.userService.getIdByUserName(request.userName())).equals(request.passwd())) {
				return request;
			}
		}
		return new LoginUserDTO(null, null);
	}

	@PostMapping("/registerUser")
	public User registerUser(@RequestBody User user) { return this.userService.createUser(user); }

	//	☝️
	//MAIN METHODS FOR WALL

	@PutMapping(path = "/{id}")
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
