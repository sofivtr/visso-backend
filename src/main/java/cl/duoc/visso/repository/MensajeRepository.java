package cl.duoc.visso.repository;

import cl.duoc.visso.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

    // Buscar mensajes por estado
    List<Mensaje> findByEstado(String estado);

    // Buscar mensajes ordenados por fecha de creación descendente
    List<Mensaje> findAllByOrderByFechaCreacionDesc();
}
