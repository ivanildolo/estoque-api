package com.ti.estoque.controllers;

import com.ti.estoque.models.Product;
import com.ti.estoque.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
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

	// Endpoint para entrada de produtos
	@PostMapping("/stock-in/{id}")
	public ResponseEntity<Product> stockIn(@PathVariable Long id, @RequestParam Integer quantity) {
		Product product = productService.updateStock(id, quantity);
		return ResponseEntity.ok(product);
	}

	// Endpoint para saída de produtos
	@PostMapping("/stock-out/{id}")
	public ResponseEntity<Product> stockOut(@PathVariable Long id, @RequestParam Integer quantity) {
		Product product = productService.updateStock(id, quantity);
		return ResponseEntity.ok(product);
	}

	@GetMapping("/report/excess-stock")
	public List<Product> getStockQuantityGreaterThan(@RequestParam int maxQuantity) {
		return productService.findByStockQuantityGreaterThan(maxQuantity);
	}

	@GetMapping("/report/low-stock")
	public List<Product> getByStockQuantityLessThan(@RequestParam int minQuantity) {
		return productService.findByStockQuantityLessThan(minQuantity);
	}

	@GetMapping("/between-dates")
	public ResponseEntity<List<Product>> getProductsBetweenDates(
			@RequestParam String startDate,
			@RequestParam String endDate) {
		LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59", DateTimeFormatter.ISO_LOCAL_DATE_TIME);

		// Chamada do serviço
		List<Product> products = productService.getProductsByDateRange(start, end);
		return ResponseEntity.ok(products);
	}

}
