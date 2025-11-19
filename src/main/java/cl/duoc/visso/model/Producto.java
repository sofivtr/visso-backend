package cl.duoc.visso.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;

    @Column(name = "codigo_producto", length = 10, nullable = false, unique = true)
    private String codigoProducto;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    // ‘O’ óptico, ‘S’ sol, ‘C’ contacto, ‘A’ accesorio
    @Column(name = "tipo", nullable = false, length = 1)
    private String tipo;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    // URL o ruta relativa a la imagen (ej: /images/rayban001.png)
    @Column(name = "imagen_url", length = 255)
    private String imagenUrl;

    // Relaciones

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_marca", nullable = false)
    private Marca marca;
}
