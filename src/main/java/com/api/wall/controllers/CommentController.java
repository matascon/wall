package com.api.wall.controllers;

import com.api.wall.dto.DataCommentDTO;
import com.api.wall.dto.ResponseCommentDTO;
import com.api.wall.models.Comment;
import com.api.wall.services.CommentService;
import com.api.wall.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	private final CommentService commentService;
	private final UserService userService;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	public CommentController(CommentService commentService, UserService userService) {
		this.commentService = commentService;
		this.userService = userService;
	}

	@GetMapping("/getComments/{postId}")
	public ResponseEntity<List<ResponseCommentDTO>> getCommentsByPostId(@PathVariable int postId) {
		try {
			List<ResponseCommentDTO> comments = this.commentService.getCommentsByPostId(postId);
			return ResponseEntity.status(HttpStatus.OK).body(comments);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping("/createComment")
	public ResponseEntity<ResponseCommentDTO> createComment(@RequestBody DataCommentDTO dataCommentDTO) {
		if (this.userService.getPasswdById(this.userService.getIdByUserName(dataCommentDTO.getUserName())).equals(dataCommentDTO.getPasswd())) {
			try {
				ResponseCommentDTO responseCommentDTO = this.commentService.createComment(dataCommentDTO);
				messagingTemplate.convertAndSend("/topic/comments/" + dataCommentDTO.getPostId(), responseCommentDTO);
				return ResponseEntity.status(HttpStatus.CREATED).body(responseCommentDTO);
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	}
}
