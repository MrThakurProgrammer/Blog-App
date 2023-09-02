package com.blog.services;

import java.util.List;

import com.blog.payloads.CategoryDto;

public interface CategoryService {
	
	//Create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//Update
	CategoryDto UpdateCategory(CategoryDto categoryDto, Integer categoryId);
	
	//Delete
	void deleteCategory( Integer categoryId);
	
	//Get
	CategoryDto getCategory(Integer categoryId);
	
	//Get All
	List<CategoryDto> getAllCategories();

}
