package com.ti.estoque.services;

import com.ti.estoque.dto.ProductDTO;
import com.ti.estoque.enums.MovementType;
import com.ti.estoque.exceptions.ResourceNotFoundException;
import com.ti.estoque.models.Category;
import com.ti.estoque.models.Product;
import com.ti.estoque.models.Movement;
import com.ti.estoque.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    ProductService(ProductRepository productRepository, CategoryService categoryService){
         this.productRepository = productRepository;
         this.categoryService = categoryService;
    }

    public Product saveProduct(Product product) {
        product.getMovements().add(new Movement(product, product.getQuantity(), MovementType.ENTRY, product.getLocation(), product.getPrice()));
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductDTO productUpdated) {
        Category category = categoryService.getCategoryById(productUpdated.getCategoryId());
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        product.setName(productUpdated.getName());
        product.setCategory(category);
        product.setPrice(productUpdated.getPrice());
        product.setLocation(productUpdated.getLocation());
        if (productUpdated.getQuantity() > product.getQuantity()) {
            int quantity = productUpdated.getQuantity() - product.getQuantity();
            product.getMovements().add(new Movement(product, quantity, MovementType.ENTRY, productUpdated.getLocation(), product.getPrice()));
        }
        if (productUpdated.getQuantity() < product.getQuantity()) {
            int quantity =  product.getQuantity() - productUpdated.getQuantity();

            product.getMovements().add(new Movement(product, quantity, MovementType.EXIT, productUpdated.getLocation(),product.getPrice()));
        }
        product.setQuantity(productUpdated.getQuantity());

        return productRepository.save(product);
    }

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Produto %s não encontrado", id)));
    }

    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public void addStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        product.setQuantity(product.getQuantity() + quantity);
        product.getMovements().add(new Movement(product, quantity, MovementType.ENTRY, product.getLocation(), product.getPrice()));
        productRepository.save(product);
    }

    @Transactional
    public void removeStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Quantidade indisponível no estoque.");
        }

        product.setQuantity(product.getQuantity() - quantity);
        product.getMovements().add(new Movement(product, quantity, MovementType.EXIT, product.getLocation(), product.getPrice()));
        productRepository.save(product);
    }

    public List<Product> findByStockQuantityLessThan(int minimumQuantity) {
        return productRepository.findByQuantityLessThan(minimumQuantity);
    }

    public List<Product> findByStockQuantityGreaterThan(int maxQuantity) {
        return productRepository.findByQuantityGreaterThan(maxQuantity);
    }

    public List<Product> getProductsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return productRepository.findByCreatedAtBetween(startDate, endDate);
    }

    public List<Product> getProductsByDateRangeAndName(LocalDateTime start, LocalDateTime end, String name) {
        if (name == null || name.isEmpty()) {
            return productRepository.findByCreatedAtBetween(start, end);
        } else {
            return productRepository.findByCreatedAtBetweenAndNameContainingIgnoreCase(start, end, name);
        }
    }

}
