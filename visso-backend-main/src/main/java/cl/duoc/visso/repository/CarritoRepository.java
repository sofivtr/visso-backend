package cl.duoc.visso.repository;

import cl.duoc.visso.model.Carrito;
import cl.duoc.visso.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    // Carrito ACTIVO del usuario ('A')
    Optional<Carrito> findByUsuarioAndEstado(Usuario usuario, String estado);
}
