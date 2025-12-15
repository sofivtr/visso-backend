package cl.duoc.visso.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class OpenApiConfig {

    @Value("${openapi.server.urls:http://localhost:8081}")
    private String serverUrls;

    @Bean
    public OpenAPI vissoOpenAPI() {
        List<Server> servers = Arrays.stream(serverUrls.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(url -> new Server().url(url))
                .collect(Collectors.toList());

        return new OpenAPI()
                .info(new Info()
                        .title("Visso API")
                        .description("Documentación de la API para la aplicación óptica Visso")
                        .version("v1")
                        .contact(new Contact().name("Visso").email("contacto@visso.cl"))
                        .license(new License().name("MIT"))
                )
                .servers(servers)
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
