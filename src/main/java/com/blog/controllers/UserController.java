package com.blog.controllers;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//Post: Create User
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto  createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<UserDto>(createUserDto, HttpStatus.CREATED);
	}
	
	//Put: Update User
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId)
	{
		UserDto  updateUserDto = this.userService.updateUser(userDto,userId);
		return  ResponseEntity.ok(updateUserDto);
	}
	
//	//Delete: Delete User	
//	@DeleteMapping("/{userId}")
//	public ResponseEntity<ApiResponse> deleteUser( @PathVariable Integer userId)
//	{
//		this.userService.deleteUser(userId);		
//		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully !!",true), HttpStatus.OK);
//	}
	
	//Delete: Delete User	
	//ADMIN
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser( @PathVariable Integer userId)
	{
		this.userService.deleteUser(userId);		
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully !!",true), HttpStatus.OK);
	}
	
	//Get: All User 
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers()
	{
		return ResponseEntity.ok(this.userService.getAllUser());
	}
	
	//Get:Single User 
		@GetMapping("/{userId}")
		public ResponseEntity<UserDto> getUser( @PathVariable Integer userId)
		{
			return ResponseEntity.ok(this.userService.getUserById(userId));
		}
	
}
