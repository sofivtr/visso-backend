package cl.duoc.visso.controller;

import cl.duoc.visso.model.Cotizacion;
import cl.duoc.visso.model.Usuario;
import cl.duoc.visso.repository.CotizacionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cotizaciones")
@CrossOrigin(origins = "*")
public class CotizacionController {

    private final CotizacionRepository cotizacionRepository;

    public CotizacionController(CotizacionRepository cotizacionRepository) {
        this.cotizacionRepository = cotizacionRepository;
    }

    // Crear cotización
    @PostMapping
    public ResponseEntity<Cotizacion> crear(@RequestBody Cotizacion cotizacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cotizacionRepository.save(cotizacion));
    }

    // Historial por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Cotizacion>> listarPorUsuario(@PathVariable Long usuarioId) {
        Usuario u = new Usuario();
        u.setId(usuarioId);
        return ResponseEntity.ok(cotizacionRepository.findByUsuario(u));
    }
}