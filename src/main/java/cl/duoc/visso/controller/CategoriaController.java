package cl.duoc.visso.controller;

import cl.duoc.visso.model.Categoria;
import cl.duoc.visso.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listar() {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Long id) {
        return categoriaService.obtenerCategoriaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Categoria> crear(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.guardarCategoria(categoria));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Categoria> categoriaOpt = categoriaService.obtenerCategoriaPorId(id);
        
        if (categoriaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        // Verificar si tiene productos asociados
        if (categoriaService.tieneProductosAsociados(id)) {
            return ResponseEntity.badRequest()
                    .body("No se puede eliminar la categoría porque tiene productos asociados");
        }
        
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}