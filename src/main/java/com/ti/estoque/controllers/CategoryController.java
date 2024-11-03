package com.ti.estoque.controllers;

import com.ti.estoque.models.Category;
import com.ti.estoque.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

	private final CategoryService categoryService;

	CategoryController(CategoryService categoryService){
		this.categoryService = categoryService;
	}

	@GetMapping
	public ResponseEntity<List<Category>> listAll() {
		return ResponseEntity.ok(categoryService.getAllCategories());
	}

}
