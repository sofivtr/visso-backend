package cl.duoc.visso.model;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(name = "Producto", description = "Producto disponible en la tienda")
@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    @Schema(description = "Identificador único del producto", example = "15")
    private Long id;

    @Column(name = "codigo_producto", length = 10, nullable = false, unique = true)
    @Schema(description = "Código único del producto", example = "SOL-RB-02")
    private String codigoProducto;

    @Column(name = "nombre", nullable = false, length = 150)
    @Schema(description = "Nombre del producto", example = "Lente de sol Ray-Ban clubmaster")
    private String nombre;

    @Column(name = "descripcion", length = 500)
    @Schema(description = "Descripción del producto", example = "Estilo retro vintage con marco combinado")
    private String descripcion;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    @Schema(description = "Precio del producto", example = "92990.00")
    private BigDecimal precio;

    @Column(name = "stock", nullable = false)
    @Schema(description = "Stock disponible", example = "14")
    private Integer stock;

    @Column(name = "fecha_creacion", nullable = false)
    @Schema(description = "Fecha de creación", example = "2025-11-18")
    private LocalDate fechaCreacion;

    @Column(name = "imagen_url", length = 255)
    @Schema(description = "URL de la imagen del producto", example = "/images/PRODUCTOS/SOL/s_7.webp")
    private String imagenUrl;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    @Schema(description = "Categoría del producto")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_marca", nullable = false)
    @Schema(description = "Marca del producto")
    private Marca marca;
}
