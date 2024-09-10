package com.example.projetoSpring.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
               .info(new Info()
                       .title("API - Projeto Spring Back-End")
                       .version("1.0.0")
                       .description("API para gerenciamento de dados"))
                .tags(
                        Arrays.asList(
                           new Tag().name("Imagem de usuário").description("Imagem do usuário para o perfil."),
                           new Tag().name("Usuário").description("processo dos usuários")
                        )
                );
    }

}
