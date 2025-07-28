package com.api.wall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DataPost {
	private Integer id;
	private String title;
	private String content;
	private String createdAt;
	private String userName;

	public DataPost(Integer id, String title, String content, String createdAt, String userName) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.userName = userName;
	}
}
