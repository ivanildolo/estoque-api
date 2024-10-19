package com.ti.estoque.services;

import com.ti.estoque.enums.MovementType;
import com.ti.estoque.exceptions.ResourceNotFoundException;
import com.ti.estoque.models.Product;
import com.ti.estoque.models.ProductMovement;
import com.ti.estoque.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public Product updateStockEntry(Long id, Integer quantityChange) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com ID " + id + " não encontrado."));

        if (product.getStockQuantity() + quantityChange <= 0) {
            throw new IllegalArgumentException("Quantidade tem que ser maior que 0.");
        }

        product.setStockQuantity(product.getStockQuantity() + quantityChange);
        product.getMovements().add(new ProductMovement(product, quantityChange, MovementType.ENTRY));
        return productRepository.save(product);
    }

    public Product updateStockExit(Long id, Integer quantityChange) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com ID " + id + " não encontrado."));

        if (product.getStockQuantity() + quantityChange <= 0) {
            throw new IllegalArgumentException("Quantidade tem que ser maior que 0.");
        }

        product.setStockQuantity(product.getStockQuantity() + quantityChange);
        product.getMovements().add(new ProductMovement(product, quantityChange, MovementType.EXIT));
        return productRepository.save(product);
    }

    public List<Product> findByStockQuantityLessThan(int minimumQuantity) {
        return productRepository.findByStockQuantityLessThan(minimumQuantity);
    }

    public List<Product> findByStockQuantityGreaterThan(int maxQuantity) {
        return productRepository.findByStockQuantityGreaterThan(maxQuantity);
    }

    public List<Product> getProductsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return productRepository.findBycreationDateBetween(startDate, endDate);
    }

    public List<Product> getProductsByDateRangeAndName(LocalDateTime start, LocalDateTime end, String name) {
        if (name == null || name.isEmpty()) {
            // Se o nome não foi informado, busca apenas pelo intervalo de datas
            return productRepository.findBycreationDateBetween(start, end);
        } else {
            // Se o nome foi informado, busca pelo nome e intervalo de datas
            return productRepository.findByCreationDateBetweenAndNameContainingIgnoreCase(start, end, name);
        }
    }

}
