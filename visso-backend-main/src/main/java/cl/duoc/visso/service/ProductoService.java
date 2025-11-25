package cl.duoc.visso.service;

import cl.duoc.visso.model.Producto;
import cl.duoc.visso.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    // Inyección por constructor
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // Obtener todos los productos
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // Obtener un producto por ID
    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    // Crear un nuevo producto
    public Producto crearProducto(Producto producto) {
        // acá podrías validar cosas si quieres
        return productoRepository.save(producto);
    }

    // Actualizar un producto existente
    public Optional<Producto> actualizarProducto(Long id, Producto productoActualizado) {
        return productoRepository.findById(id).map(productoExistente -> {
            productoExistente.setCodigoProducto(productoActualizado.getCodigoProducto());
            productoExistente.setNombre(productoActualizado.getNombre());
            productoExistente.setDescripcion(productoActualizado.getDescripcion());
            productoExistente.setPrecio(productoActualizado.getPrecio());
            productoExistente.setStock(productoActualizado.getStock());
            productoExistente.setTipo(productoActualizado.getTipo());
            productoExistente.setFechaCreacion(productoActualizado.getFechaCreacion());
            productoExistente.setImagenUrl(productoActualizado.getImagenUrl());
            productoExistente.setCategoria(productoActualizado.getCategoria());
            productoExistente.setMarca(productoActualizado.getMarca());

            return productoRepository.save(productoExistente);
        });
    }

    // Eliminar un producto
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
