package cl.duoc.visso.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(name = "Mensaje", description = "Mensaje de contacto/soporte")
@Entity
@Table(name = "mensaje")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    @Schema(description = "Identificador único del mensaje", example = "1")
    private Long id;

    @Column(nullable = false, length = 150)
    @Schema(description = "Nombre del remitente", example = "Juan Pérez")
    private String nombre;

    @Column(nullable = false, length = 150)
    @Schema(description = "Email del remitente", example = "juan@example.com")
    private String email;

    @Column(nullable = false, length = 200)
    @Schema(description = "Asunto del mensaje", example = "Consulta sobre productos")
    private String asunto;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Schema(description = "Contenido del mensaje")
    private String mensaje;

    @Column(name = "fecha_creacion", nullable = false)
    @Schema(description = "Fecha de creación del mensaje")
    private LocalDateTime fechaCreacion;

    @Column(nullable = false, length = 20)
    @Schema(description = "Estado del mensaje", example = "PENDIENTE")
    private String estado; // PENDIENTE, RESPONDIDO

    @PrePersist
    protected void onCreate() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
        if (estado == null) {
            estado = "PENDIENTE";
        }
    }
}
