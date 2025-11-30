package cl.duoc.visso.controller;

import cl.duoc.visso.model.Producto;
import cl.duoc.visso.model.Categoria;
import cl.duoc.visso.model.Marca;
import cl.duoc.visso.service.ProductoService;
import cl.duoc.visso.service.CategoriaService;
import cl.duoc.visso.service.MarcaService;
import cl.duoc.visso.service.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final MarcaService marcaService;
    private final FileStorageService fileStorageService;

    public ProductoController(ProductoService productoService, 
                            CategoriaService categoriaService,
                            MarcaService marcaService,
                            FileStorageService fileStorageService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.marcaService = marcaService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CRUD: Crear con imagen
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Producto> crearProducto(
            @RequestParam("codigoProducto") String codigoProducto,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") String precio,
            @RequestParam("stock") String stock,
            @RequestParam("categoriaId") Long categoriaId,
            @RequestParam("marcaId") Long marcaId,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        
        // Buscar categoria y marca
        Categoria categoria = categoriaService.obtenerCategoriaPorId(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + categoriaId));
        Marca marca = marcaService.obtenerMarcaPorId(marcaId)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con ID: " + marcaId));
        
        Producto producto = new Producto();
        producto.setCodigoProducto(codigoProducto);
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(new java.math.BigDecimal(precio));
        producto.setStock(Integer.parseInt(stock));
        producto.setCategoria(categoria);
        producto.setMarca(marca);
        
        if (imagen != null && !imagen.isEmpty()) {
            String imagenUrl = fileStorageService.storeProductImage(imagen, categoria.getNombre());
            producto.setImagenUrl(imagenUrl);
        }
        
        Producto creado = productoService.crearProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // CRUD: Actualizar con imagen
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable Long id,
            @RequestParam("codigoProducto") String codigoProducto,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precio") String precio,
            @RequestParam("stock") String stock,
            @RequestParam("categoriaId") Long categoriaId,
            @RequestParam("marcaId") Long marcaId,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        
        // Buscar categoria y marca
        Categoria categoria = categoriaService.obtenerCategoriaPorId(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + categoriaId));
        Marca marca = marcaService.obtenerMarcaPorId(marcaId)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con ID: " + marcaId));
        
        Producto producto = new Producto();
        producto.setCodigoProducto(codigoProducto);
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(new java.math.BigDecimal(precio));
        producto.setStock(Integer.parseInt(stock));
        producto.setCategoria(categoria);
        producto.setMarca(marca);
        
        if (imagen != null && !imagen.isEmpty()) {
            productoService.obtenerProductoPorId(id).ifPresent(p -> {
                if (p.getImagenUrl() != null) {
                    fileStorageService.deleteFile(p.getImagenUrl());
                }
            });
            String imagenUrl = fileStorageService.storeProductImage(imagen, categoria.getNombre());
            producto.setImagenUrl(imagenUrl);
        }
        
        return productoService.actualizarProducto(id, producto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CRUD: Eliminar con imagen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.obtenerProductoPorId(id).ifPresent(p -> {
            if (p.getImagenUrl() != null) {
                fileStorageService.deleteFile(p.getImagenUrl());
            }
        });
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}