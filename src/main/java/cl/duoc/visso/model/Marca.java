package cl.duoc.visso.model;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Marca", description = "Marca de producto")
@Entity
@Table(name = "marca")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_marca")
    @Schema(description = "Identificador único de la marca", example = "4")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    @Schema(description = "Nombre de la marca", example = "Ray-Ban")
    private String nombre;

    @Column(name = "imagen", length = 255)
    @Schema(description = "URL de la imagen de la marca", example = "/images/MARCAS/rayban.webp")
    private String imagen;
}
