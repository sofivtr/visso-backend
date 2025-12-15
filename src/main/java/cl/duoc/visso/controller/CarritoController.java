package cl.duoc.visso.controller;

import cl.duoc.visso.dto.SolicitudCarrito;
import cl.duoc.visso.dto.ActualizarCantidadRequest;
import cl.duoc.visso.dto.MensajeResponse;
import cl.duoc.visso.model.Carrito;
import cl.duoc.visso.model.DatosTransaccion;
import cl.duoc.visso.repository.DetalleCarritoRepository;
import cl.duoc.visso.service.CarritoService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "*")
@Tag(name = "Carrito")
public class CarritoController {

    private final CarritoService carritoService;
    private final DetalleCarritoRepository detalleCarritoRepository;

    public CarritoController(CarritoService carritoService, DetalleCarritoRepository detalleCarritoRepository) {
        this.carritoService = carritoService;
        this.detalleCarritoRepository = detalleCarritoRepository;
    }

    @GetMapping("/{usuarioId}")
    @Operation(summary = "Obtener carrito activo del usuario", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Carrito> obtenerCarrito(@PathVariable @Parameter(description = "ID del usuario") Long usuarioId) {
        return ResponseEntity.ok(carritoService.obtenerCarritoActivo(usuarioId));
    }

    @PostMapping("/agregar")
    @Operation(summary = "Agregar producto al carrito", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<MensajeResponse> agregarProducto(@RequestBody SolicitudCarrito solicitud) {
        carritoService.agregarProducto(
            solicitud.getUsuarioId(), 
            solicitud.getProductoId(), 
            solicitud.getCantidad(),
            solicitud.getCotizacionId()
        );
        return ResponseEntity.ok(new MensajeResponse("Producto agregado al carrito correctamente"));
    }

    @PostMapping("/cerrar/{usuarioId}")
    @Operation(summary = "Cerrar carrito y confirmar compra con datos de envío y pago (Estado: A -> P)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<MensajeResponse> cerrarCarrito(
            @PathVariable @Parameter(description = "ID del usuario") Long usuarioId,
            @RequestBody @Parameter(description = "Datos de envío y pago") DatosTransaccion datosTransaccion) {
        carritoService.cerrarCarrito(usuarioId, datosTransaccion);
        return ResponseEntity.ok(new MensajeResponse("Compra realizada con éxito."));
    }

    @GetMapping("/cerrados")
    @Operation(summary = "Listar carritos pagados y enviados", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Carrito>> obtenerCarritosCerrados() {
        return ResponseEntity.ok(carritoService.listarVentas());
    }

    @GetMapping("/ventas")
    @Operation(summary = "Listar ventas (Estados: P y E)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Carrito>> obtenerVentas() {
        // Aquí podrías validar que el usuario sea ADMIN si usaras tokens,
        // pero por ahora está bien así.
        return ResponseEntity.ok(carritoService.listarVentas());
    }

    @DeleteMapping("/detalle/{detalleId}")
    @Operation(summary = "Eliminar detalle del carrito", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<MensajeResponse> eliminarDetalle(@PathVariable @Parameter(description = "ID del detalle") Long detalleId) {
        try {
            detalleCarritoRepository.deleteById(detalleId);
            return ResponseEntity.ok(new MensajeResponse("Producto eliminado del carrito"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MensajeResponse("Error al eliminar: " + e.getMessage()));
        }
    }

    @PutMapping("/detalle/{detalleId}")
    @Operation(summary = "Actualizar cantidad de detalle", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<MensajeResponse> actualizarCantidad(@PathVariable @Parameter(description = "ID del detalle") Long detalleId, @RequestBody ActualizarCantidadRequest body) {
        try {
            Integer cantidad = body.getCantidad();
            if (cantidad == null || cantidad < 1) {
                return ResponseEntity.badRequest().body(new MensajeResponse("Cantidad inválida"));
            }
            carritoService.actualizarCantidad(detalleId, cantidad);
            return ResponseEntity.ok(new MensajeResponse("Cantidad actualizada"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MensajeResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MensajeResponse("Error al actualizar: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{usuarioId}/limpiar")
    @Operation(summary = "Limpiar carrito", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<MensajeResponse> limpiarCarrito(@PathVariable @Parameter(description = "ID del usuario") Long usuarioId) {
        try {
            carritoService.limpiarCarrito(usuarioId);
            return ResponseEntity.ok(new MensajeResponse("Carrito vaciado"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MensajeResponse("Error al limpiar carrito: " + e.getMessage()));
        }
    }

    @PutMapping("/marcar-enviado/{carritoId}")
    @Operation(summary = "Marcar carrito como enviado (Estado: P -> E)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<MensajeResponse> marcarComoEnviado(@PathVariable @Parameter(description = "ID del carrito") Long carritoId) {
        try {
            carritoService.marcarComoEnviado(carritoId);
            return ResponseEntity.ok(new MensajeResponse("Carrito marcado como enviado"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MensajeResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MensajeResponse("Error al marcar como enviado: " + e.getMessage()));
        }
    }
}
