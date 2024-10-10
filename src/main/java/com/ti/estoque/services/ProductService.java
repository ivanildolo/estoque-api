package com.ti.estoque.services;

import com.ti.estoque.models.Product;
import com.ti.estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProdutoRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productUpdated) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        product.setName(productUpdated.getName());
        product.setCategory(productUpdated.getCategory());
        product.setStockQuantity(productUpdated.getStockQuantity());
        product.setPrice(productUpdated.getPrice());
        product.setWarehouseLocation(productUpdated.getWarehouseLocation());
        return productRepository.save(product);
    }

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Product updateStock(Long id, Integer quantidade) {
        Product produto = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.setStockQuantity(produto.getStockQuantity() + quantidade);
        return productRepository.save(produto);
    }
}
