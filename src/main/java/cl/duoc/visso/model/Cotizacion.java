package cl.duoc.visso.model;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(name = "Cotizacion", description = "Cotización de lentes ópticos")
@Entity
@Table(name = "cotizacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cotizacion")
    @Schema(description = "Identificador único de la cotización", example = "10")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @Schema(description = "Usuario asociado a la cotización")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    @Schema(description = "Producto óptico cotizado")
    private Producto producto;

    // --- Datos del Paciente ---
    @Column(name = "nombre_paciente", nullable = false, length = 150)
    @Schema(description = "Nombre del paciente", example = "Juan Pérez")
    private String nombrePaciente;

    @Column(name = "fecha_receta", nullable = false)
    @Schema(description = "Fecha de la receta", example = "2025-11-18")
    private LocalDate fechaReceta;

    // --- Graduaciones (Numéricos) ---
    @Column(name = "grado_od") // Ojo Derecho
    @Schema(description = "Graduación ojo derecho", example = "-1.25")
    private Double gradoOd;

    @Column(name = "grado_oi") // Ojo Izquierdo
    @Schema(description = "Graduación ojo izquierdo", example = "-1.00")
    private Double gradoOi;

    // --- Preferencias del Lente ---

    // Lista Desplegable (Dropdown) en Android
    // Opciones sugeridas: "Monofocal", "Bifocal", "Progresivo"
    @Column(name = "tipo_lente", length = 50)
    @Schema(description = "Tipo de lente", example = "Progresivo")
    private String tipoLente;

    // Radio Button en Android
    // Opciones sugeridas: "Blanco" (Transparente), "Fotocromático" (Oscurece con sol)
    @Column(name = "tipo_cristal", length = 50)
    @Schema(description = "Tipo de cristal", example = "Fotocromático")
    private String tipoCristal;

    // --- Tratamientos Adicionales (Checkboxes) ---
    
    // Checkbox (Sí/No)
    @Column(name = "antirreflejo")
    @Schema(description = "Tratamiento antirreflejo", example = "true")
    private Boolean antirreflejo;

    // Checkbox (Sí/No)
    @Column(name = "filtro_azul")
    @Schema(description = "Filtro de luz azul", example = "true")
    private Boolean filtroAzul;

    // Valor informativo calculado en la App
    @Column(name = "valor_aprox", precision = 10, scale = 2)
    @Schema(description = "Valor aproximado total", example = "59990.00")
    private BigDecimal valorAprox;
}
