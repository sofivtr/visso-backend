package cl.duoc.visso.service;

import cl.duoc.visso.model.Categoria;
import cl.duoc.visso.repository.CategoriaRepository;
import cl.duoc.visso.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    // Listar todas (Para llenar el combo en Android)
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id);
    }
    
    // Guardar (Por si quieres crear categorias desde Postman)
    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
    
    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
    
    public boolean tieneProductosAsociados(Long id) {
        return categoriaRepository.findById(id)
                .map(productoRepository::existsByCategoria)
                .orElse(false);
    }
}