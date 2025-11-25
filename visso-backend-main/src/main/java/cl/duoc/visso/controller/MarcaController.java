package cl.duoc.visso.controller;

import cl.duoc.visso.model.Marca;
import cl.duoc.visso.service.MarcaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@CrossOrigin(origins = "*")
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping
    public ResponseEntity<List<Marca>> listar() {
        return ResponseEntity.ok(marcaService.listarMarcas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> obtenerPorId(@PathVariable Long id) {
        return marcaService.obtenerMarcaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Marca> crear(@RequestBody Marca marca) {
        return ResponseEntity.ok(marcaService.guardarMarca(marca));
    }
} 