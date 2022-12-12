package br.com.fink.filialapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI filialOpenAPI() {
        return new OpenAPI()
            .components(new Components())
            .info(new Info()
                .title("API CRUD Filiais")
                .description("Permite todas as operações para manutenção de Filiais")
                .termsOfService("termos")
                .contact(new Contact().email("email@fink.com"))
                .license(new License().name("GNU"))
                .version("1.0")
            );

    }    
}
