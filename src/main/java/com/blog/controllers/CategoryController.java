package com.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	//Create
	@PostMapping("/")
	public ResponseEntity<CategoryDto>createCategories(@Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto createCat = this.categoryService.createCategory(categoryDto);		
		return new ResponseEntity<CategoryDto>(createCat, HttpStatus.CREATED);
	}
	
	//Update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto>updateCategories(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer catId)
	{
		CategoryDto updateCat = this.categoryService.UpdateCategory(categoryDto, catId);		
		return new ResponseEntity<CategoryDto>(updateCat, HttpStatus.OK);
	}
	
	
	//Delete
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategories(@PathVariable Integer catId)
	{
		this.categoryService.deleteCategory(catId);		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully !!",true), HttpStatus.OK);
	}
		
	//Get All
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategories()
	{
		List<CategoryDto> getCat = this.categoryService.getAllCategories();
		return ResponseEntity.ok(getCat);
	}
	
	//Get 
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getAllCategories(@PathVariable Integer catId)
	{
		CategoryDto getAllCat = this.categoryService.getCategory(catId);
		
		return new ResponseEntity<CategoryDto>(getAllCat, HttpStatus.OK);
	}
}
