package cl.duoc.visso.repository;

import cl.duoc.visso.model.Carrito;
import cl.duoc.visso.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    // Carrito ACTIVO del usuario ('A')
    Optional<Carrito> findByUsuarioAndEstado(Usuario usuario, String estado);

    // Buscar carritos por un estado específico
    List<Carrito> findByEstado(String estado);
    
    // Buscar carritos por múltiples estados (Para ventas: 'P' y 'E')
    List<Carrito> findByEstadoIn(List<String> estados);
}
