package com.ti.estoque.controllers;

import com.ti.estoque.dto.ProductDTO;
import com.ti.estoque.dto.ProductSearchDTO;
import com.ti.estoque.dto.StockOperationDTO;
import com.ti.estoque.models.Category;
import com.ti.estoque.models.Product;
import com.ti.estoque.services.CategoryService;
import com.ti.estoque.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/products")
public class ProdutoController {

	private final ProductService productService;
	private final CategoryService categoryService;

	ProdutoController(ProductService productService, CategoryService categoryService){
		this.productService = productService;
		this.categoryService = categoryService;
	}

	@PostMapping
	public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO prod) {
		Category category = categoryService.getCategoryById(prod.getCategoryId());
		Product product = new Product();
		product.setName(prod.getName());
		product.setCategory(category);
		product.setPrice(prod.getPrice());
		product.setQuantity(prod.getQuantity());
		product.setDescription(prod.getDescription());
		product.setLocation(prod.getLocation());
		Product savedProduct = productService.saveProduct(product);
		return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO ProductUupdated) {
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

	@PostMapping(value = "/stock-in", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> stockIn(@Valid @RequestBody StockOperationDTO stockOperationDTO) {
		productService.addStock(stockOperationDTO.getProductId(), stockOperationDTO.getQuantity());
		return ResponseEntity.ok("");
	}

	@PostMapping("/stock-out")
	public ResponseEntity<String> stockOut(@Valid @RequestBody StockOperationDTO stockOperationDTO) {
		productService.removeStock(stockOperationDTO.getProductId(), stockOperationDTO.getQuantity());
		return ResponseEntity.ok("");
	}

	@GetMapping("/report/excess-stock")
	public List<Product> getStockQuantityGreaterThan(@RequestParam int maxQuantity) {
		return productService.findByStockQuantityGreaterThan(maxQuantity);
	}

	@GetMapping("/report/low-stock")
	public List<Product> getByStockQuantityLessThan(@RequestParam int minQuantity) {
		return productService.findByStockQuantityLessThan(minQuantity);
	}

	@PostMapping("/search")
	public ResponseEntity<List<Product>> getProductsBetweenDates(
			@Valid @RequestBody ProductSearchDTO productSearchDTO) {

		LocalDateTime start = LocalDateTime.parse(productSearchDTO.getStartDate() + "T00:00:00",
				DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		LocalDateTime end = LocalDateTime.parse(productSearchDTO.getEndDate() + "T23:59:59",
				DateTimeFormatter.ISO_LOCAL_DATE_TIME);

		List<Product> products = productService.getProductsByDateRangeAndName(start, end, productSearchDTO.getName());
		return ResponseEntity.ok(products);
	}

}
