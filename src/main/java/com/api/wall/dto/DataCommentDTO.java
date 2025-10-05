package com.api.wall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataCommentDTO {
	private String content;
	private String userName;
	private String passwd;
	private Integer postId;
}
