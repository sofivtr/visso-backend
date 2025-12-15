package cl.duoc.visso.service;

import cl.duoc.visso.model.Marca;
import cl.duoc.visso.repository.MarcaRepository;
import cl.duoc.visso.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaService {

    private final MarcaRepository marcaRepository;
    private final ProductoRepository productoRepository;

    public MarcaService(MarcaRepository marcaRepository, ProductoRepository productoRepository) {
        this.marcaRepository = marcaRepository;
        this.productoRepository = productoRepository;
    }

    public List<Marca> listarMarcas() {
        return marcaRepository.findAll();
    }

    public Optional<Marca> obtenerMarcaPorId(Long id) {
        return marcaRepository.findById(id);
    }
    
    public Marca guardarMarca(Marca marca) {
        return marcaRepository.save(marca);
    }

    public void eliminarMarca(Long id) {
        marcaRepository.deleteById(id);
    }
    
    public boolean tieneProductosAsociados(Long id) {
        return marcaRepository.findById(id)
                .map(productoRepository::existsByMarca)
                .orElse(false);
    }
}