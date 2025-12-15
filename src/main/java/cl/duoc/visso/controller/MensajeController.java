package cl.duoc.visso.controller;

import cl.duoc.visso.dto.MensajeResponse;
import cl.duoc.visso.model.Mensaje;
import cl.duoc.visso.service.MensajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mensajes")
@CrossOrigin(origins = "*")
@Tag(name = "Mensajes de Contacto", description = "Endpoints para mensajes de contacto y soporte")
public class MensajeController {

    private final MensajeService mensajeService;

    public MensajeController(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    /**
     * Crear un nuevo mensaje de contacto (Endpoint público)
     */
    @PostMapping
    @Operation(summary = "Enviar mensaje de contacto (Público)", description = "Cualquier usuario puede enviar un mensaje de contacto")
    public ResponseEntity<MensajeResponse> crearMensaje(@RequestBody Mensaje mensaje) {
        try {
            mensajeService.guardarMensaje(mensaje);
            return ResponseEntity.ok(new MensajeResponse("Mensaje enviado con éxito. Nos pondremos en contacto contigo pronto."));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MensajeResponse("Error al enviar mensaje: " + e.getMessage()));
        }
    }

    /**
     * Listar todos los mensajes (Solo ADMIN)
     */
    @GetMapping
    @Operation(summary = "Listar todos los mensajes (Solo ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Mensaje>> listarMensajes() {
        return ResponseEntity.ok(mensajeService.listarTodos());
    }

    /**
     * Listar mensajes por estado (Solo ADMIN)
     */
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar mensajes por estado (Solo ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Mensaje>> listarPorEstado(
            @PathVariable @Parameter(description = "Estado del mensaje: PENDIENTE o RESPONDIDO") String estado) {
        return ResponseEntity.ok(mensajeService.listarPorEstado(estado));
    }

    /**
     * Cambiar estado de un mensaje (Solo ADMIN)
     */
    @PutMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de mensaje (Solo ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<MensajeResponse> cambiarEstado(
            @PathVariable @Parameter(description = "ID del mensaje") Long id,
            @RequestBody Map<String, String> request) {
        try {
            String nuevoEstado = request.get("estado");
            if (nuevoEstado == null || nuevoEstado.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new MensajeResponse("El estado es requerido"));
            }
            mensajeService.cambiarEstado(id, nuevoEstado);
            return ResponseEntity.ok(new MensajeResponse("Estado actualizado correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MensajeResponse("Error: " + e.getMessage()));
        }
    }

    /**
     * Obtener un mensaje por ID (Solo ADMIN)
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener mensaje por ID (Solo ADMIN)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Mensaje> obtenerMensaje(
            @PathVariable @Parameter(description = "ID del mensaje") Long id) {
        try {
            return ResponseEntity.ok(mensajeService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
