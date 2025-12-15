package cl.duoc.visso.repository;

import cl.duoc.visso.model.Carrito;
import cl.duoc.visso.model.Cotizacion;
import cl.duoc.visso.model.DetalleCarrito;
import cl.duoc.visso.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DetalleCarritoRepository extends JpaRepository<DetalleCarrito, Long> {
    
    List<DetalleCarrito> findByCarrito(Carrito carrito);
    
    // Busca si ya existe este producto CON esa cotización específica
    Optional<DetalleCarrito> findByCarritoAndProductoAndCotizacion(Carrito carrito, Producto producto, Cotizacion cotizacion);

    // Busca si ya existe este producto SIN cotización (Lentes de sol, accesorios)
    Optional<DetalleCarrito> findByCarritoAndProductoAndCotizacionIsNull(Carrito carrito, Producto producto);
    
    // Verifica si un producto está asociado a algún carrito
    boolean existsByProducto(Producto producto);
}