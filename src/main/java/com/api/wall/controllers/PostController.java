package com.api.wall.controllers;

import com.api.wall.dto.DataPostDTO;
import com.api.wall.dto.ResponsePostDTO;
import com.api.wall.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping
	public List<ResponsePostDTO> getPosts() { return this.postService.getPosts(); }

	@GetMapping(path="/{id}")
	public ResponsePostDTO getPostById(@PathVariable int id) { return postService.getPostById(id); }

	@PostMapping("/createPost")
	public boolean createPost(@RequestBody DataPostDTO dataPost) {
		return this.postService.createPost(dataPost) != null;
	}

	@PutMapping(path="/updatePost/{id}")
	public boolean updatePost(@RequestBody DataPostDTO dataPost, @PathVariable int id) {
		return this.postService.updatePost(dataPost, id) != null;
	}

	@DeleteMapping("/deletePost/{id}")
	public boolean deletePost(@PathVariable int id) {
		return this.postService.deletePost(id);
	}
}
