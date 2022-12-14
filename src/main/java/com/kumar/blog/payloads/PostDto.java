package com.kumar.blog.payloads;

import java.util.Date;

import com.kumar.blog.entities.Category;
import com.kumar.blog.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private Integer postId;
	
	private String title;
	
	private String content;
//	private String imageName ="default.png";
//	private Date addedDate;
// get url or id for one to many
	
	private String imageName;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	private UserDto  user;
}