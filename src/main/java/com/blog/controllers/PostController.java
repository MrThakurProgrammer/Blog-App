package com.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstants;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;
	
@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	PostService postService;

	@Autowired
	FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	//Created
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@Valid
			@RequestBody PostDto postDto, 
			@PathVariable Integer userId, 
			@PathVariable Integer categoryId
			)
	{		
		PostDto createPost = postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}
	
	//Get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId)
	{
		List<PostDto> posts = this.postService.getPostByUser(userId);
		
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	//Get by category
	@GetMapping("/category/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer userId)
	{
		List<PostDto> posts = this.postService.getPostByCategory(userId);		
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	//update post
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId)
	{
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	
//	//***** Get all post 1 *****
//	@GetMapping("/")
//	public ResponseEntity<List<PostDto>> getAllPost()
//	{
//		List<PostDto> getPost = this.postService.getAllPost();
//		
//		return new ResponseEntity<List<PostDto>>(getPost, HttpStatus.OK);
//	}
	
	//Get all post 2
	@GetMapping("/")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
			)
	{
		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	//Get post by id
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		
		PostDto postById = this.postService.getPostById(postId);
		
		return new ResponseEntity<PostDto>(postById, HttpStatus.OK);
	}

	//Delete post 
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId)
	{
		this.postService.deletePost(postId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully !!",true), HttpStatus.OK);
	}
	
	//Search
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords){
		
		List<PostDto> searchPosts = this.postService.searchPosts(keywords);
		
		return new ResponseEntity<List<PostDto>>(searchPosts, HttpStatus.OK);
	}
	
	//Post image upload
	@PostMapping("/image/upload/{postId}")
	ResponseEntity<PostDto> uploadPostImage(
			@RequestParam MultipartFile image,
			@PathVariable Integer postId) throws IOException
	{
		
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	
	//Method to serve files
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName, 
			HttpServletResponse response
			)throws IOException
	{
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
}
