package com.api.wall.services;

import com.api.wall.dto.DataCommentDTO;
import com.api.wall.dto.ResponseCommentDTO;

import java.util.List;

public interface ICommentService {
	public List<ResponseCommentDTO> getCommentsByPostId(int postId);

	public ResponseCommentDTO createComment(DataCommentDTO dataComment);
}
