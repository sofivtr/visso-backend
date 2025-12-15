package cl.duoc.visso.controller;

import cl.duoc.visso.model.Categoria;
import cl.duoc.visso.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de categorías")
    })
    public ResponseEntity<List<Categoria>> listar() {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable @Parameter(description = "ID de la categoría") Long id) {
        return categoriaService.obtenerCategoriaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(summary = "Crear categoría")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoría creada")
    })
    public ResponseEntity<Categoria> crear(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.guardarCategoria(categoria));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoría")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoría actualizada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
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
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Categoría eliminada"),
        @ApiResponse(responseCode = "400", description = "No se puede eliminar (asociaciones)"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
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
