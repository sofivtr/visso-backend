package cl.duoc.visso.model;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Categoria", description = "Categoría de productos")
@Entity
@Table(name = "categoria")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    @Schema(description = "Identificador único de la categoría", example = "2")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    @Schema(description = "Nombre de la categoría", example = "Lentes ópticos")
    private String nombre;
}
