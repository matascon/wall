package com.api.wall.controllers;

import com.api.wall.dto.DataPostDTO;
import com.api.wall.dto.FileUrlDTO;
import com.api.wall.dto.ResponsePostDTO;
import com.api.wall.models.Post;
import com.api.wall.services.CloudinaryService;
import com.api.wall.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
	private final PostService postService;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private CloudinaryService cloudinaryService;

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

	@PostMapping("/uploadImage")
	public ResponseEntity<FileUrlDTO> uploadImage(@RequestParam("file") MultipartFile file) {
		try {
			String fileName = file.getOriginalFilename();
			if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".webp")) {
				String imageUrl = cloudinaryService.uploadImage(file);
				return ResponseEntity.ok(new FileUrlDTO(imageUrl));
			}
			return ResponseEntity.ok(null);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new FileUrlDTO("There was an error while uploading the image"));
		}
	}

	@PostMapping("/createPost")
	public ResponsePostDTO createPost(@RequestBody DataPostDTO dataPostDTO) {
		Post post = postService.createPost(dataPostDTO);

		ResponsePostDTO responsePostDto = new ResponsePostDTO(
				post.getId(),
				post.getTitle(),
				post.getContent(),
				post.getCreatedAt(),
				post.getFileUrl(),
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
	*/
	@DeleteMapping("/deletePost/{id}")
	public boolean deletePost(@PathVariable int id) {
		return this.postService.deletePost(id);
	}

}
