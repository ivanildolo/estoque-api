package com.ti.estoque.controllers;

import java.util.List;

import com.ti.estoque.models.Product;
import com.ti.estoque.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProdutoController {

	@Autowired
	private ProductService productService;

	@PostMapping
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
		Product newProduct = productService.saveProduct(product);
		return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product ProductUupdated) {
		Product productUupdatedBd = productService.updateProduct(id, ProductUupdated);
		return ResponseEntity.ok(productUupdatedBd);
	}

	@PutMapping("/update-stock/{id}")
	public ResponseEntity<Product> updateStock(@PathVariable Long id, @RequestParam Integer quantity) {
		Product product = productService.updateStock(id, quantity);
		return ResponseEntity.ok(product);
	}

	@GetMapping
	public ResponseEntity<List<Product>> listAll() {
		return ResponseEntity.ok(productService.listAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		return ResponseEntity.ok(productService.findById(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
