package cl.duoc.visso.controller;

import cl.duoc.visso.model.Marca;
import cl.duoc.visso.service.MarcaService;
import cl.duoc.visso.service.FileStorageService;

import java.util.Optional;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@CrossOrigin(origins = "*")
@Tag(name = "Marcas")
public class MarcaController {

    private final MarcaService marcaService;
    private final FileStorageService fileStorageService;

    public MarcaController(MarcaService marcaService, FileStorageService fileStorageService) {
        this.marcaService = marcaService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    @Operation(summary = "Listar marcas")
    public ResponseEntity<List<Marca>> listar() {
        return ResponseEntity.ok(marcaService.listarMarcas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener marca por ID")
    public ResponseEntity<Marca> obtenerPorId(@PathVariable @Parameter(description = "ID de la marca") Long id) {
        return marcaService.obtenerMarcaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Crear marca")
    public ResponseEntity<Marca> crear(
            @RequestParam("nombre") String nombre,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        
        Marca marca = new Marca();
        marca.setNombre(nombre);
        
        if (imagen != null && !imagen.isEmpty()) {
            String imagenUrl = fileStorageService.storeMarcaImage(imagen);
            marca.setImagen(imagenUrl);
        }
        
        return ResponseEntity.ok(marcaService.guardarMarca(marca));
    }
    
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Actualizar marca")
    public ResponseEntity<Marca> actualizar(
            @PathVariable @Parameter(description = "ID de la marca") Long id,
            @RequestParam("nombre") String nombre,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        
        return marcaService.obtenerMarcaPorId(id)
            .map(marcaExistente -> {
                marcaExistente.setNombre(nombre);
                
                // Si hay nueva imagen, borrar la antigua y guardar la nueva
                if (imagen != null && !imagen.isEmpty()) {
                    if (marcaExistente.getImagen() != null) {
                        fileStorageService.deleteFile(marcaExistente.getImagen());
                    }
                    String imagenUrl = fileStorageService.storeMarcaImage(imagen);
                    marcaExistente.setImagen(imagenUrl);
                }
                
                return ResponseEntity.ok(marcaService.guardarMarca(marcaExistente));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar marca")
    public ResponseEntity<?> eliminar(@PathVariable @Parameter(description = "ID de la marca") Long id) {
        Optional<Marca> marcaOpt = marcaService.obtenerMarcaPorId(id);
        
        if (marcaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        // Verificar si tiene productos asociados
        if (marcaService.tieneProductosAsociados(id)) {
            return ResponseEntity.badRequest()
                    .body("No se puede eliminar la marca porque tiene productos asociados");
        }
        
        Marca marca = marcaOpt.get();
        if (marca.getImagen() != null) {
            fileStorageService.deleteFile(marca.getImagen());
        }
        marcaService.eliminarMarca(id);
        
        return ResponseEntity.noContent().build();
    }
}
