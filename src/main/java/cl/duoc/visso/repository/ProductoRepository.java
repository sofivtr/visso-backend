package cl.duoc.visso.repository;

import cl.duoc.visso.model.Categoria;
import cl.duoc.visso.model.Marca;
import cl.duoc.visso.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Por si quieres buscar por código de producto
    boolean existsByCodigoProducto(String codigoProducto);
    
    // Verificar si existen productos asociados a una marca
    boolean existsByMarca(Marca marca);
    
    // Verificar si existen productos asociados a una categoría
    boolean existsByCategoria(Categoria categoria);
}
