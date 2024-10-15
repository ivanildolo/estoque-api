package com.ti.estoque.seed;

import com.ti.estoque.enums.MovementType;
import com.ti.estoque.models.Product;
import com.ti.estoque.models.ProductMovement;
import com.ti.estoque.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

        List<Product> products = new ArrayList<>();
        products.add(new Product( "Laptop", "Eletrônicos", 10, 2999.99, "Corredor 3, Prateleira C, Bin 8", "Laptop 16GB RAM"));
        products.add(new Product("Smartphone", "Eletrônicos", 30, 1999.99, "Corredor 1, Prateleira A, Bin 1", "Smartphone 48MP"));
        products.add(new Product( "Headphones", "Acessórios", 150, 199.99, "Corredor 2, Prateleira B, Bin 3", "Headphones Noise Cancelling"));

        Product laptop = products.get(0);
        laptop.getMovements().add(new ProductMovement(laptop, 50, MovementType.ENTRY));
        laptop.getMovements().add(new ProductMovement(laptop, 40, MovementType.EXIT));

        Product smartphone = products.get(1);
        smartphone.getMovements().add(new ProductMovement(smartphone, 100, MovementType.ENTRY));
        smartphone.getMovements().add(new ProductMovement(smartphone, 70, MovementType.EXIT));

        Product headphones = products.get(2);
        headphones.getMovements().add(new ProductMovement(headphones, 200, MovementType.ENTRY));
        headphones.getMovements().add(new ProductMovement(headphones, 50, MovementType.EXIT));

        productRepository.saveAll(products);

        System.out.println("Produtos iniciais carregados no banco de dados.");
    }
}