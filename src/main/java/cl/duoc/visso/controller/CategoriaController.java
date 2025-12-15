package cl.duoc.visso.controller;

import cl.duoc.visso.model.Categoria;
import cl.duoc.visso.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
@Tag(name = "Categorías")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    @Operation(summary = "Listar categorías")
    public ResponseEntity<List<Categoria>> listar() {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría por ID")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable @Parameter(description = "ID de la categoría") Long id) {
        return categoriaService.obtenerCategoriaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(summary = "Crear categoría")
    public ResponseEntity<Categoria> crear(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.guardarCategoria(categoria));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoría")
    public ResponseEntity<Categoria> actualizar(
            @PathVariable @Parameter(description = "ID de la categoría") Long id,
            @RequestBody Categoria categoria) {
        Optional<Categoria> categoriaOpt = categoriaService.obtenerCategoriaPorId(id);
        
        if (categoriaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        categoria.setId(id);
        return ResponseEntity.ok(categoriaService.guardarCategoria(categoria));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar categoría")
    public ResponseEntity<?> eliminar(@PathVariable @Parameter(description = "ID de la categoría") Long id) {
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
