package cl.duoc.visso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/productos/**").permitAll()
                .requestMatchers("/api/categorias/**").permitAll()
                .requestMatchers("/api/marcas/**").permitAll()
                .requestMatchers("/images/**").permitAll()
                // Endpoints que requieren autenticación (implementar JWT después)
                .requestMatchers("/api/usuarios/**").permitAll() // Por ahora permitAll hasta implementar JWT
                .requestMatchers("/api/carrito/**").permitAll()
                .requestMatchers("/api/cotizaciones/**").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }
}