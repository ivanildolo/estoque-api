package com.ti.estoque.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("ivanildosilvalima@gmail.com");
        contact.setName("Ivanildo Silva");
        contact.setUrl("https://www.linkedin.com/in/ivanildo-silva-de-lima-b67b52b1");

        License mitLicense =
                new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info =
                new Info()
                        .title("Estoque API")
                        .version("1.0.0")
                        .contact(contact)
                        .description("Documentação da API de Gerenciamento de Estoque.")
                        .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
