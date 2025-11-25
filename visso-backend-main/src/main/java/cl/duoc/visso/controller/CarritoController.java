package cl.duoc.visso.controller;

import cl.duoc.visso.dto.SolicitudCarrito;
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

    @PostMapping("/agregar")
    public ResponseEntity<?> agregarProducto(@RequestBody SolicitudCarrito solicitud) {
        carritoService.agregarProducto(
            solicitud.getUsuarioId(), 
            solicitud.getProductoId(), 
            solicitud.getCantidad(),
            solicitud.getCotizacionId()
        );
        return ResponseEntity.ok("Producto agregado al carrito correctamente");
    }

    @PostMapping("/cerrar/{usuarioId}")
    public ResponseEntity<?> cerrarCarrito(@PathVariable Long usuarioId) {
        carritoService.cerrarCarrito(usuarioId);
        return ResponseEntity.ok("Compra realizada con éxito.");
    }
}