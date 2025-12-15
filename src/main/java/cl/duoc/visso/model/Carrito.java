package cl.duoc.visso.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(name = "Carrito", description = "Carrito de compras del usuario")
@Entity
@Table(name = "carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito")
    @Schema(description = "Identificador único del carrito", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @Schema(description = "Usuario propietario del carrito")
    private Usuario usuario;

    @Column(name = "fecha_creacion")
    @Schema(description = "Fecha y hora de creación", example = "2025-11-18T12:34:56")
    private LocalDateTime fechaCreacion;

    @Column(name = "estado")
    @Schema(description = "Estado actual del carrito", example = "activo")
    private String estado;

    @Column(name = "total")
    @Schema(description = "Monto total del carrito", example = "129990.00")
    private BigDecimal total;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_datos_transaccion")
    @Schema(description = "Datos de envío y pago de la transacción")
    private DatosTransaccion datosTransaccion;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference 
    @Schema(description = "Lista de ítems del carrito")
    private List<DetalleCarrito> detalles = new ArrayList<>();
}
