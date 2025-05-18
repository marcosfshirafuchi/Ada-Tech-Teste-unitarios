package tech.ada.ml_users.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ML-USERS")
                        .version("v1.0")
                        .description("Esta é uma API INCRÍVEL desenvolvida no módulo de web II do meli.")
                        .contact(new Contact()
                                .name("Rodolfo")
                                .url("http://rodolfo.com")
                                .email("rodolfo@minhaapi.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}
