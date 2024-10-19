package com.ti.estoque.services;

import com.ti.estoque.enums.MovementType;
import com.ti.estoque.exceptions.ResourceNotFoundException;
import com.ti.estoque.models.Product;
import com.ti.estoque.models.ProductMovement;
import com.ti.estoque.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) {
        product.getMovements().add(new ProductMovement(product, product.getStockQuantity(), MovementType.ENTRY));
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productUpdated) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        product.setName(productUpdated.getName());
        product.setCategory(productUpdated.getCategory());
        product.setPrice(productUpdated.getPrice());
        product.setWarehouseLocation(productUpdated.getWarehouseLocation());
        if (productUpdated.getStockQuantity() > product.getStockQuantity()) {
            int quantity = productUpdated.getStockQuantity() - product.getStockQuantity();
            product.getMovements().add(new ProductMovement(product, quantity, MovementType.ENTRY));
        }
        if (productUpdated.getStockQuantity() < product.getStockQuantity()) {
            int quantity =  product.getStockQuantity() - productUpdated.getStockQuantity();

            product.getMovements().add(new ProductMovement(product, quantity, MovementType.EXIT));
        }
        product.setStockQuantity(productUpdated.getStockQuantity());

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
        product.setStockQuantity(product.getStockQuantity() + quantity);
        product.getMovements().add(new ProductMovement(product, quantity, MovementType.ENTRY));
        productRepository.save(product);
    }

    @Transactional
    public void removeStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

        if (product.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("Quantidade indisponível no estoque.");
        }

        product.setStockQuantity(product.getStockQuantity() - quantity);
        product.getMovements().add(new ProductMovement(product, quantity, MovementType.EXIT));
        productRepository.save(product);
    }

    public List<Product> findByStockQuantityLessThan(int minimumQuantity) {
        return productRepository.findByStockQuantityLessThan(minimumQuantity);
    }

    public List<Product> findByStockQuantityGreaterThan(int maxQuantity) {
        return productRepository.findByStockQuantityGreaterThan(maxQuantity);
    }

    public List<Product> getProductsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return productRepository.findByCreationDateBetween(startDate, endDate);
    }

    public List<Product> getProductsByDateRangeAndName(LocalDateTime start, LocalDateTime end, String name) {
        if (name == null || name.isEmpty()) {
            return productRepository.findByCreationDateBetween(start, end);
        } else {
            return productRepository.findByCreationDateBetweenAndNameContainingIgnoreCase(start, end, name);
        }
    }

}
