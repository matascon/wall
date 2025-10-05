package com.api.wall.controllers;

import com.api.wall.dto.DataPostDTO;
import com.api.wall.dto.FileUrlDTO;
import com.api.wall.dto.ResponsePostDTO;
import com.api.wall.models.Post;
import com.api.wall.services.CloudinaryService;
import com.api.wall.services.PostService;
import com.api.wall.services.UserService;
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
	private final UserService userService;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private CloudinaryService cloudinaryService;

	public PostController(PostService postService, UserService userService) {
		this.postService = postService;
		this.userService = userService;
	}

	@GetMapping("/getPosts/{numberPostsPrinted}")
	public List<ResponsePostDTO> getPostsForPostList(@PathVariable int numberPostsPrinted) {
		return this.postService.getPostsForPostList(5, numberPostsPrinted);
	}

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
	public ResponseEntity<ResponsePostDTO> createPost(@RequestBody DataPostDTO dataPostDTO) {
		if (this.userService.getPasswdById(this.userService.getIdByUserName(dataPostDTO.getUserName())).equals(dataPostDTO.getPasswd())) {
			Post post = this.postService.createPost(dataPostDTO);

			ResponsePostDTO responsePostDTO = new ResponsePostDTO(
					post.getId(),
					post.getTitle(),
					post.getContent(),
					post.getCreatedAt(),
					post.getFileUrl(),
					post.getUser().getUserName());
			messagingTemplate.convertAndSend("/topic/posts", responsePostDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(responsePostDTO);
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	}
}
