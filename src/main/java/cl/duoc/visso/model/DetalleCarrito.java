package cl.duoc.visso.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Schema(name = "DetalleCarrito", description = "Detalle de ítems dentro de un carrito")
@Entity
@Table(name = "detalle_carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    @Schema(description = "Identificador único del detalle", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_carrito", nullable = false)
    @JsonBackReference 
    @Schema(description = "Carrito asociado al detalle")
    private Carrito carrito;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto", nullable = true, foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    @Schema(description = "Producto del detalle (puede ser nulo si es cotización)")
    private Producto producto;

    @Column(name = "nombre_producto", nullable = false, length = 150)
    @Schema(description = "Nombre del producto mostrado", example = "Lente de sol Ray-Ban clubmaster")
    private String nombreProducto;

    @ManyToOne
    @JoinColumn(name = "id_cotizacion")
    @Schema(description = "Cotización asociada si aplica")
    private Cotizacion cotizacion;

    @Column(name = "cantidad")
    @Schema(description = "Cantidad solicitada", example = "2")
    private Integer cantidad;

    @Column(name = "precio_unitario")
    @Schema(description = "Precio unitario del ítem", example = "92990.00")
    private BigDecimal precioUnitario;
}
