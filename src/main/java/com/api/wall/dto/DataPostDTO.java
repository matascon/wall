package com.api.wall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataPostDTO {
	private String title;
	private String content;
	private String userName;
}
