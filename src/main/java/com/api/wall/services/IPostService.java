package com.api.wall.services;

import com.api.wall.dto.DataPostDTO;
import com.api.wall.dto.ResponsePostDTO;
import com.api.wall.models.Post;

import java.util.List;

public interface IPostService {
	public List<ResponsePostDTO> getPosts();

	public ResponsePostDTO getPostById(int postId);

	public Post createPost(DataPostDTO dataPost);

	public Post updatePost(DataPostDTO dataPost, int id);

	public boolean deletePost(int postId);

}
