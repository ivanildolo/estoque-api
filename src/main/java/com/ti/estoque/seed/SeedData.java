package com.ti.estoque.seed;

import com.ti.estoque.enums.MovementType;
import com.ti.estoque.models.Category;
import com.ti.estoque.models.Movement;
import com.ti.estoque.models.Product;
import com.ti.estoque.repository.CategoryRepository;
import com.ti.estoque.repository.MovementRepository;
import com.ti.estoque.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Profile("dev")  // Este componente será ativado apenas no perfil de desenvolvimento
public class SeedData implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MovementRepository movementRepository;

    @Override
    public void run(String... args) {
        seedDatabase();
    }

    private void seedDatabase() {
        if (productRepository.count() > 0) {
            return;
        }

        // Criar categorias
        Category category1 = new Category();
        category1.setName("Notebooks");
        category1.setDescription("Notebooks de varias marcas");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("Celulares");
        category2.setDescription("Celulares de varias marcas");
        categoryRepository.save(category2);

        Category category3 = new Category();
        category3.setName("Games");
        category3.setDescription("Consoles, PC game, notebooks game, acessórios");
        categoryRepository.save(category3);

        Category category4 = new Category();
        category4.setName("Eletrônicos");
        category4.setDescription("Eletronicos em geral");
        categoryRepository.save(category4);

        // Criar produtos 1
        Product product1 = new Product();
        product1.setName("Laptop");
        product1.setPrice(BigDecimal.valueOf(1000.00));
        product1.setCategory(category1);
        product1.setQuantity(10);
        product1.setLocation("Corredor 1, Prateleira 1");
        product1.setDescription("laptop sony HD 2TB 16GB Ram");
        productRepository.save(product1);

        Movement movement1 = new Movement();
        movement1.setProduct(product1); // Associar produto
        movement1.setQuantity(10);
        movement1.setPrice(BigDecimal.valueOf(1000));
        movement1.setMovementType(MovementType.ENTRY);
        movement1.setLocation("Corredor 1, Prateleira 1");
        movementRepository.save(movement1);

        // Criar produtos 2
        Product product2 = new Product();
        product2.setName("Celular Samsung");
        product2.setPrice(BigDecimal.valueOf(150.00));
        product2.setCategory(category2);
        product2.setQuantity(150);
        product2.setLocation("Corredor 6, Prateleira 1");
        product2.setDescription("Celular Samsung 8G Ram 256GB HD");
        productRepository.save(product2);

        Movement movement2 = new Movement();
        movement2.setProduct(product2); // Associar produto
        movement2.setQuantity(100);
        movement2.setPrice(BigDecimal.valueOf(150));
        movement2.setLocation("Corredor 6, Prateleira 1");
        movement2.setMovementType(MovementType.ENTRY); // Exemplo: "OUT" para saída
        movementRepository.save(movement2);

        // Criar produtos 3
        Product product3 = new Product();
        product3.setName("Xbox one");
        product3.setPrice(BigDecimal.valueOf(2300.00));
        product3.setCategory(category3);
        product3.setQuantity(12);
        product3.setLocation("Corredor 2, Prateleira 1");
        product3.setDescription("Xbox one 1TB slim");
        productRepository.save(product3);

        Movement movement3 = new Movement();
        movement3.setProduct(product3); // Associar produto
        movement3.setQuantity(100);
        movement3.setPrice(BigDecimal.valueOf(12));
        movement3.setLocation("Corredor 6, Prateleira 1");
        movement3.setMovementType(MovementType.ENTRY); // Exemplo: "OUT" para saída
        movementRepository.save(movement3);

        System.out.println("Produtos iniciais carregados no banco de dados.");
    }
}