package com.ti.estoque.services;

import com.ti.estoque.exceptions.ResourceNotFoundException;
import com.ti.estoque.models.Category;
import com.ti.estoque.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Categoria %s não encontrado", id)));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
