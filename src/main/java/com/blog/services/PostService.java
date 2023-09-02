package com.blog.services;

import java.util.List;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {
	
	//Create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//Update
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//Delete
	void deletePost(Integer postId);
	
//	//*****Get all posts 1 *****
//	List<PostDto> getAllPost();	
	
	//Get all posts 2
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);	
	
	//Get single post
	PostDto getPostById(Integer postId);
	
	//Get all post by category
	List<PostDto> getPostByCategory(Integer categoryId);
	
	//Get all post by user
	List<PostDto> getPostByUser(Integer userId);
		
	//Searching Posts
	List<PostDto> searchPosts(String keyword);
}
