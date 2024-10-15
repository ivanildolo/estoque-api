package com.ti.estoque.seed;

import com.ti.estoque.models.Product;
import com.ti.estoque.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Profile("dev")  // Este componente será ativado apenas no perfil de desenvolvimento
public class SeedData implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) {
        seedDatabase();
    }

    private void seedDatabase() {
        if (productRepository.count() > 0) {
            return; // Se já existem produtos, não insere novos
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Product[] products = new Product[]{
                new Product(null, "Laptop", "Eletrônicos", 10, 2999.99, "A1", "Laptop com 16GB de RAM e 512GB SSD", LocalDate.now().format(formatter)),
                new Product(null, "Smartphone", "Eletrônicos", 20, 1999.99, "A2", "Smartphone com câmera de 48MP", LocalDate.now().format(formatter)),
                new Product(null, "Tablet", "Eletrônicos", 15, 1499.99, "B1", "Tablet com tela de 10 polegadas", LocalDate.now().format(formatter)),
                new Product(null, "Fone de Ouvido", "Acessórios", 30, 299.99, "C1", "Fone de ouvido sem fio", LocalDate.now().format(formatter)),
                new Product(null, "Teclado Mecânico", "Acessórios", 25, 499.99, "C2", "Teclado mecânico RGB", LocalDate.now().format(formatter)),
                new Product(null, "Mouse", "Acessórios", 50, 199.99, "C3", "Mouse gamer com DPI ajustável", LocalDate.now().format(formatter)),
                new Product(null, "Monitor", "Eletrônicos", 5, 799.99, "D1", "Monitor Full HD 24 polegadas", LocalDate.now().format(formatter)),
                new Product(null, "Impressora", "Periféricos", 8, 699.99, "D2", "Impressora a laser colorida", LocalDate.now().format(formatter)),
                new Product(null, "HD Externo", "Armazenamento", 12, 399.99, "E1", "HD externo 1TB", LocalDate.now().format(formatter)),
                new Product(null, "Câmera Digital", "Fotografia", 10, 2999.99, "F1", "Câmera DSLR com lente intercambiável", LocalDate.now().format(formatter)),
                new Product(null, "Microfone", "Áudio", 20, 499.99, "G1", "Microfone condensador USB", LocalDate.now().format(formatter)),
                new Product(null, "Projetor", "Áudio/Visual", 4, 1599.99, "H1", "Projetor Full HD portátil", LocalDate.now().format(formatter)),
                new Product(null, "Smartwatch", "Wearables", 18, 999.99, "I1", "Smartwatch com monitor de batimentos cardíacos", LocalDate.now().format(formatter)),
                new Product(null, "Caixa de Som", "Áudio", 22, 399.99, "J1", "Caixa de som Bluetooth", LocalDate.now().format(formatter)),
                new Product(null, "Carregador Portátil", "Acessórios", 40, 149.99, "K1", "Carregador portátil com 10000mAh", LocalDate.now().format(formatter)),
                new Product(null, "Cabo HDMI", "Acessórios", 35, 49.99, "L1", "Cabo HDMI de 2 metros", LocalDate.now().format(formatter)),
        };

        for (Product product : products) {
            productRepository.save(product);
        }
    }
}
