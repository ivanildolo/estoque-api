package com.ti.estoque.controllers;

import com.ti.estoque.dto.ProductDTO;
import com.ti.estoque.dto.ProductSearchDTO;
import com.ti.estoque.dto.StockOperationDTO;
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
	public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO prod) {
		Product product = new Product();
		product.setName(prod.getName());
		product.setCategory(prod.getCategory());
		product.setPrice(prod.getPrice());
		product.setStockQuantity(prod.getStockQuantity());
		product.setDescription(prod.getDescription());
		product.setWarehouseLocation(prod.getWarehouseLocation());
		Product savedProduct = productService.saveProduct(product);
		return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
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

	@PostMapping("/stock-in")
	public ResponseEntity<String> stockIn(@Valid @RequestBody StockOperationDTO stockOperationDTO) {
		productService.addStock(stockOperationDTO.getProductId(), stockOperationDTO.getQuantity());
		return ResponseEntity.ok("Estoque atualizado com sucesso.");
	}

	@PostMapping("/stock-out")
	public ResponseEntity<String> stockOut(@Valid @RequestBody StockOperationDTO stockOperationDTO) {
		productService.removeStock(stockOperationDTO.getProductId(), stockOperationDTO.getQuantity());
		return ResponseEntity.ok("Estoque reduzido com sucesso.");
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
