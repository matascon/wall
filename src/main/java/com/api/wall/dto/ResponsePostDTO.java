package com.api.wall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePostDTO {
	private Integer id;
	private String title;
	private String content;
	private LocalDateTime createdAt;
	private String userName;
}
