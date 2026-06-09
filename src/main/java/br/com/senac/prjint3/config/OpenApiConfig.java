package br.com.senac.prjint3.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Backend PRJINT3 API - Lanchonete")
                        .version("v1")
                        .description("API REST com CRUD, Spring Boot, Java 21, MySQL e exclusão lógica por status.")
                        .license(new License().name("Uso acadêmico")));
    }
}
