package cl.duoc.visso.controller;

import cl.duoc.visso.dto.SolicitudCarrito; // Importamos la cajita
import cl.duoc.visso.model.Carrito;
import cl.duoc.visso.service.CarritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "*")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Carrito> obtenerCarrito(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(carritoService.obtenerCarritoActivo(usuarioId));
    }

    // Aquí usamos el DTO (SolicitudCarrito)
    // La App enviará un JSON: { "usuarioId": 1, "productoId": 2, "cantidad": 1 }
    // Spring automáticamente meterá esos datos en nuestra clase SolicitudCarrito
    @PostMapping("/agregar")
    public ResponseEntity<?> agregarProducto(@RequestBody SolicitudCarrito solicitud) {
        
        carritoService.agregarProducto(
            solicitud.getUsuarioId(), 
            solicitud.getProductoId(), 
            solicitud.getCantidad()
        );

        return ResponseEntity.ok("Producto agregado al carrito correctamente");
    }
}