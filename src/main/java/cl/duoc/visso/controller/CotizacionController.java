package cl.duoc.visso.controller;

import cl.duoc.visso.model.Cotizacion;
import cl.duoc.visso.model.Usuario;
import cl.duoc.visso.repository.CotizacionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/cotizaciones")
@CrossOrigin(origins = "*")
@Tag(name = "Cotizaciones")
public class CotizacionController {

    private final CotizacionRepository cotizacionRepository;

    public CotizacionController(CotizacionRepository cotizacionRepository) {
        this.cotizacionRepository = cotizacionRepository;
    }

    // Crear cotización
    @PostMapping
    @Operation(summary = "Crear cotización", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Cotizacion> crear(@RequestBody Cotizacion cotizacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cotizacionRepository.save(cotizacion));
    }

    // Historial por usuario
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar cotizaciones por usuario", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<Cotizacion>> listarPorUsuario(@PathVariable @Parameter(description = "ID del usuario") Long usuarioId) {
        Usuario u = new Usuario();
        u.setId(usuarioId);
        return ResponseEntity.ok(cotizacionRepository.findByUsuario(u));
    }
}
