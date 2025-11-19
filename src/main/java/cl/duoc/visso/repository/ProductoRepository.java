package cl.duoc.visso.repository;

import cl.duoc.visso.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Por si quieres buscar por código de producto
    boolean existsByCodigoProducto(String codigoProducto);
}
