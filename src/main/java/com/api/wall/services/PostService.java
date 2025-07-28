package com.api.wall.services;

import com.api.wall.dto.DataPost;
import com.api.wall.models.Post;
import com.api.wall.models.User;
import com.api.wall.repositories.IPostRepository;
import com.api.wall.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
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
	public List<DataPost> getPosts() {
		List<Post> posts = postRepository.findAll();

		return posts.stream()
				.map(post -> new DataPost(
						post.getId(),
						post.getTitle(),
						post.getContent(),
						post.getCreatedAt(),
						post.getUser().getUserName()
				)).collect(Collectors.toList());
	}

	@Override
	public Post getPostById(int id) {
		return this.postRepository.findById(id).orElse(null);
	}

	@Override
	public Post createPost(DataPost post) {
		User user = this.userRepository.findByUserName(post.getUserName());

		Post newPost = new Post();
		newPost.setTitle(post.getTitle());
		newPost.setContent(post.getContent());
		newPost.setCreatedAt(post.getCreatedAt());
		newPost.setUser(user);

		return this.postRepository.save(newPost);
	}

	@Override
	public Post updatePost(DataPost post) {
		Post postToUpdate = this.postRepository.findById(post.getId()).orElse(null);
		if (postToUpdate != null) {
			postToUpdate.setTitle(post.getTitle());
			postToUpdate.setContent(post.getContent());
			postToUpdate.setCreatedAt(post.getCreatedAt());
			postToUpdate.setUser(this.userRepository.findByUserName(post.getUserName()));
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
