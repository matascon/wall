package com.api.wall.services;

import com.api.wall.dto.DataPost;
import com.api.wall.models.Post;

import javax.xml.crypto.Data;
import java.util.List;

public interface IPostService {
	public List<DataPost> getPosts();

	public Post getPostById(int postId);

	public Post createPost(DataPost post);

	public Post updatePost(DataPost post);

	public boolean deletePost(int postId);

}
