package cl.duoc.visso.repository;

import cl.duoc.visso.model.DatosTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatosTransaccionRepository extends JpaRepository<DatosTransaccion, Long> {
}
