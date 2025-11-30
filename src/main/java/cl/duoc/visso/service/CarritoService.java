package cl.duoc.visso.service;

import cl.duoc.visso.model.*;
import cl.duoc.visso.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final DetalleCarritoRepository detalleRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final CotizacionRepository cotizacionRepository;

    public CarritoService(CarritoRepository carritoRepository, 
                          DetalleCarritoRepository detalleRepository, 
                          UsuarioRepository usuarioRepository, 
                          ProductoRepository productoRepository,
                          CotizacionRepository cotizacionRepository) {
        this.carritoRepository = carritoRepository;
        this.detalleRepository = detalleRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.cotizacionRepository = cotizacionRepository;
    }

    public Carrito obtenerCarritoActivo(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        return carritoRepository.findByUsuarioAndEstado(usuario, "A")
                .orElseGet(() -> {
                    Carrito nuevo = new Carrito();
                    nuevo.setUsuario(usuario);
                    nuevo.setFechaCreacion(LocalDateTime.now());
                    nuevo.setEstado("A"); // A = Activo
                    nuevo.setTotal(BigDecimal.ZERO);
                    return carritoRepository.save(nuevo);
                });
    }

    @Transactional
    public void agregarProducto(Long usuarioId, Long productoId, Integer cantidad, Long cotizacionId) {
        Carrito carrito = obtenerCarritoActivo(usuarioId);
        Producto producto = productoRepository.findById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Optional<DetalleCarrito> existente;

        // LÓGICA: ¿Es óptico (con cotización) o normal?
        if (cotizacionId != null) {
            Cotizacion cotizacion = cotizacionRepository.findById(cotizacionId)
                    .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));
            
            existente = detalleRepository.findByCarritoAndProductoAndCotizacion(carrito, producto, cotizacion);
            
            if (existente.isPresent()) {
                DetalleCarrito det = existente.get();
                det.setCantidad(det.getCantidad() + cantidad);
                detalleRepository.save(det);
            } else {
                crearDetalle(carrito, producto, cantidad, cotizacion);
            }
        } else {
            existente = detalleRepository.findByCarritoAndProductoAndCotizacionIsNull(carrito, producto);

            if (existente.isPresent()) {
                DetalleCarrito det = existente.get();
                det.setCantidad(det.getCantidad() + cantidad);
                detalleRepository.save(det);
            } else {
                crearDetalle(carrito, producto, cantidad, null);
            }
        }
        recalcularTotal(carrito);
    }

    private void crearDetalle(Carrito carrito, Producto producto, Integer cantidad, Cotizacion cotizacion) {
        DetalleCarrito detalle = new DetalleCarrito();
        detalle.setCarrito(carrito);
        detalle.setProducto(producto);
        detalle.setNombreProducto(producto.getNombre()); // Copiar nombre para histórico
        detalle.setCantidad(cantidad);
        
        // Si tiene cotización, usar el precio calculado (valorAprox), sino usar precio base del producto
        if (cotizacion != null && cotizacion.getValorAprox() != null) {
            detalle.setPrecioUnitario(cotizacion.getValorAprox());
        } else {
            detalle.setPrecioUnitario(producto.getPrecio());
        }
        
        detalle.setCotizacion(cotizacion);
        detalleRepository.save(detalle);
    }

    private void recalcularTotal(Carrito carrito) {
        List<DetalleCarrito> detalles = detalleRepository.findByCarrito(carrito);
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleCarrito d : detalles) {
            total = total.add(d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad())));
        }
        carrito.setTotal(total);
        carritoRepository.save(carrito);
    }

    // Cierra el carrito para simular la compra finalizada
    public Carrito cerrarCarrito(Long usuarioId) {
        Carrito carrito = obtenerCarritoActivo(usuarioId);
        carrito.setEstado("C"); // C = Cerrado/Comprado
        return carritoRepository.save(carrito);
    }

    // Limpiar carrito (eliminar todos los detalles)
    public void limpiarCarrito(Long usuarioId) {
        Carrito carrito = obtenerCarritoActivo(usuarioId);
        List<DetalleCarrito> detalles = detalleRepository.findByCarrito(carrito);
        detalleRepository.deleteAll(detalles);
        recalcularTotal(carrito);
    }

    // Listar todas las ventas (Carritos Cerrados)
    public List<Carrito> listarVentas() {
        return carritoRepository.findByEstado("C");
    }
    
}