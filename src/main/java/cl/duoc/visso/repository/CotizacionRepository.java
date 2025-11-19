package cl.duoc.visso.repository;

import cl.duoc.visso.model.Cotizacion;
import cl.duoc.visso.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {

    // Historial de cotizaciones de un usuario
    List<Cotizacion> findByUsuario(Usuario usuario);
}
