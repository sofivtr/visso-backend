package cl.duoc.visso.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class FiltroSeguridadJwt extends OncePerRequestFilter {

    private final JWTProveedor jwtTokenProvider;

    public FiltroSeguridadJwt(JWTProveedor jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Claims claims = jwtTokenProvider.validateAndGetClaims(token);
                String subject = claims.getSubject();
                String rol = claims.get("rol", String.class);
                List<SimpleGrantedAuthority> authorities = rol != null
                        ? List.of(new SimpleGrantedAuthority("ROLE_" + rol.toUpperCase()))
                        : Collections.emptyList();
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(subject, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ex) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }
}

