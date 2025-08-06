package com.api.wall.repositories;

import com.api.wall.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPostRepository extends JpaRepository<Post, Integer> {
	@Query(value = "SELECT * FROM posts ORDER BY created_at DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
	public List<Post> findPostsForPostList (@Param("limit") int limit, @Param("offset") int offset);
}
