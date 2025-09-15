package com.api.wall.repositories;

import com.api.wall.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICommentRepository extends JpaRepository<Comment, Integer> {
	@Query(value = "SELECT * FROM comments WHERE post_id = :postId ORDER BY id DESC", nativeQuery = true)
	public List<Comment> findCommentsByPostId(@Param("postId") int postId);
}
