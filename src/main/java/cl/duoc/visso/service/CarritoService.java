package cl.duoc.visso.service;

import cl.duoc.visso.model.*;
import cl.duoc.visso.repository.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final DetalleCarritoRepository detalleRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    public CarritoService(CarritoRepository carritoRepository, DetalleCarritoRepository detalleRepository, UsuarioRepository usuarioRepository, ProductoRepository productoRepository) {
        this.carritoRepository = carritoRepository;
        this.detalleRepository = detalleRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
    }

    // Busca un carrito activo ('A') o crea uno nuevo si no existe
    public Carrito obtenerCarritoActivo(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        return carritoRepository.findByUsuarioAndEstado(usuario, "A")
                .orElseGet(() -> {
                    Carrito nuevo = new Carrito();
                    nuevo.setUsuario(usuario);
                    nuevo.setFechaCreacion(LocalDateTime.now());
                    nuevo.setEstado("A");
                    nuevo.setTotal(BigDecimal.ZERO);
                    return carritoRepository.save(nuevo);
                });
    }

    // Agrega un producto al carrito
    public void agregarProducto(Long usuarioId, Long productoId, Integer cantidad) {
        Carrito carrito = obtenerCarritoActivo(usuarioId);
        Producto producto = productoRepository.findById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Crear el detalle
        DetalleCarrito detalle = new DetalleCarrito();
        detalle.setCarrito(carrito);
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(producto.getPrecio());
        
        detalleRepository.save(detalle);
        
        // Actualizar el total del carrito
        BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(cantidad));
        carrito.setTotal(carrito.getTotal().add(subtotal));
        carritoRepository.save(carrito);
    }
}