package mesh_group.test.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI getOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CRUD API")
                        .description("This API provides methods for creating, reading, updating and deleting entities.")
                        .version("1.0.0")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")))
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList("Bearer Authentication"))
                .components(
                        new Components()
                                .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}