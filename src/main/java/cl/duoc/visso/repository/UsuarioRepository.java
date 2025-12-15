package cl.duoc.visso.repository;

import cl.duoc.visso.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Para buscar por email en login/registro
    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    // Para validar RUT único
    Optional<Usuario> findByRut(String rut);
    
    boolean existsByRut(String rut);
}
