package com.api.wall.controllers;

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
	public List<User> getUsers() {return this.userService.getUsers();}

	@GetMapping(path = "/{id}")
	public User getUser(@PathVariable Integer id) {return this.userService.getUserById(id);}


	// ARREGLAR ESTO DE ABAJO!!!!!
	@PostMapping
	public boolean validateUser(@RequestBody String userName) {
		if (this.userService.getIdByUserName(userName) != null) {
			return true;
		} else {
			return false;
		}
	}

	//ARREGLAR ESTO DE ARRIBA!!!!!

	@PostMapping
	public User createUser(@RequestBody User user) {return this.userService.createUser(user);}

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
