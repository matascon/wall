package com.api.wall.controllers;

import com.api.wall.dto.DataPostDTO;
import com.api.wall.dto.ResponsePostDTO;
import com.api.wall.models.Post;
import com.api.wall.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
	private final PostService postService;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	/*
	@GetMapping
	public List<ResponsePostDTO> getPosts() { return this.postService.getPosts(); }
	 */

	@GetMapping("/getPosts/{numberPostsPrinted}")
	public List<ResponsePostDTO> getPostsForPostList(@PathVariable int numberPostsPrinted) {
		return this.postService.getPostsForPostList(5, numberPostsPrinted);
	}

	/*
	@GetMapping(path="/{id}")
	public ResponsePostDTO getPostById(@PathVariable int id) { return postService.getPostById(id); }
	*/

	@PostMapping("/createPost")
	public ResponsePostDTO createPost(@RequestBody DataPostDTO dataPostDTO) {
		Post post = postService.createPost(dataPostDTO);
		ResponsePostDTO responsePostDto = new ResponsePostDTO(
				post.getId(),
				post.getTitle(),
				post.getContent(),
				post.getCreatedAt(),
				post.getUser().getUserName());
		messagingTemplate.convertAndSend("/topic/posts", responsePostDto);
		return responsePostDto;
	}

	/*
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
	*/
}
