package com.api.wall.services;

import com.api.wall.dto.DataPostDTO;
import com.api.wall.dto.ResponsePostDTO;
import com.api.wall.models.Post;
import com.api.wall.models.User;
import com.api.wall.repositories.IPostRepository;
import com.api.wall.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService implements IPostService{
	private final IPostRepository postRepository;
	private final IUserRepository userRepository;

	public PostService(IPostRepository postRepository, IUserRepository userRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<ResponsePostDTO> getPosts() {
		List<Post> posts = postRepository.findAll();

		return posts.stream()
				.map(post -> new ResponsePostDTO(
						post.getId(),
						post.getTitle(),
						post.getContent(),
						post.getCreatedAt(),
						post.getUser().getUserName()
				)).collect(Collectors.toList());
	}

	@Override
	public ResponsePostDTO getPostById(int id) {
		Post post = this.postRepository.findById(id).orElse(null);
		if (post != null) {
			return new ResponsePostDTO(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt(), post.getUser().getUserName());
		}
		return null;
	}

	@Override
	public Post createPost(DataPostDTO dataPost) {
		User user = this.userRepository.findByUserName(dataPost.getUserName());

		Post newPost = new Post();
		newPost.setTitle(dataPost.getTitle());
		newPost.setContent(dataPost.getContent());
		newPost.setUser(user);

		return this.postRepository.save(newPost);
	}

	@Override
	public Post updatePost(DataPostDTO dataPost, int postId) {
		Post postToUpdate = this.postRepository.findById(postId).orElse(null);
		if (postToUpdate != null) {
			postToUpdate.setTitle(dataPost.getTitle());
			postToUpdate.setContent(dataPost.getContent());
			postToUpdate.setUser(this.userRepository.findByUserName(dataPost.getUserName()));
			return this.postRepository.save(postToUpdate);
		}
		return null;
	}

	@Override
	public boolean deletePost(int postId) {
		try {
			this.postRepository.deleteById(postId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
