package com.api.wall.services;

import com.api.wall.dto.DataCommentDTO;
import com.api.wall.dto.ResponseCommentDTO;
import com.api.wall.models.Comment;
import com.api.wall.models.Post;
import com.api.wall.models.User;
import com.api.wall.repositories.ICommentRepository;
import com.api.wall.repositories.IPostRepository;
import com.api.wall.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService implements ICommentService {
	private final ICommentRepository commentRepository;
	private final IUserRepository userRepository;
	private final IPostRepository postRepository;

	public CommentService(ICommentRepository commentRepository, IUserRepository userRepository, IPostRepository postRepository) {
		this.commentRepository = commentRepository;
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}

	private List<ResponseCommentDTO> findCommentsByPostId(int id) {
		List<Comment> comments = commentRepository.findCommentsByPostId(id);

		return comments.stream()
				.map(comment -> new ResponseCommentDTO(
						comment.getId(),
						comment.getContent(),
						comment.getUser().getUserName()
				)).collect(Collectors.toList());
	}

	@Override
	public List<ResponseCommentDTO> getCommentsByPostId(int postId) {
		return findCommentsByPostId(postId);
	}

	private Comment generateComment(DataCommentDTO dataComment) {
		User user = userRepository.findByUserName(dataComment.getUserName());
		Post post = postRepository.findById(dataComment.getPostId()).orElse(null);
		Comment newComment = new Comment();
		newComment.setContent(dataComment.getContent());
		newComment.setUser(user);
		newComment.setPost(post);

		return commentRepository.save(newComment);
	}

	@Override
	public ResponseCommentDTO createComment(DataCommentDTO dataComment) {
		Comment comment = generateComment(dataComment);
		ResponseCommentDTO responseCommentDTO = new ResponseCommentDTO();

		responseCommentDTO.setId(comment.getId());
		responseCommentDTO.setContent(comment.getContent());
		responseCommentDTO.setUserName(comment.getUser().getUserName());

		return responseCommentDTO;
	}
}
