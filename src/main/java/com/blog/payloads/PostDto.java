package com.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.blog.entities.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
	
	private Integer postId;
	
	@NotEmpty
	@Size(min = 4, message = "min size of category title 3 ")
	private String title;
	
	@NotEmpty
	@Size(min = 10, message = "min size of category description 10")
	private String content; 
	
	private String imageName;
	
	private Date addedDate;
	
	private UserDto user;
	
	private CategoryDto category;
	
	private Set<CommentDto> comments=new HashSet<>();
}
