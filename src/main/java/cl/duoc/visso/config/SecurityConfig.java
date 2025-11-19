package cl.duoc.visso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // desactiva CSRF para que los POST desde Postman/app funcionen
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // PERMITIR TODO por ahora
                );

        return http.build();
    }
}
