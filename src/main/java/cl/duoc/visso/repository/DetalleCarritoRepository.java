package cl.duoc.visso.repository;

import cl.duoc.visso.model.Carrito;
import cl.duoc.visso.model.DetalleCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleCarritoRepository extends JpaRepository<DetalleCarrito, Long> {

    // Todos los ítems de un carrito
    List<DetalleCarrito> findByCarrito(Carrito carrito);
}
