package cl.duoc.visso.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cotizacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cotizacion")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    // --- Datos del Paciente ---
    @Column(name = "nombre_paciente", nullable = false, length = 150)
    private String nombrePaciente;

    @Column(name = "fecha_receta", nullable = false)
    private LocalDate fechaReceta;

    // --- Graduaciones (Numéricos) ---
    @Column(name = "grado_od") // Ojo Derecho
    private Double gradoOd;

    @Column(name = "grado_oi") // Ojo Izquierdo
    private Double gradoOi;

    // --- Preferencias del Lente ---

    // Lista Desplegable (Dropdown) en Android
    // Opciones sugeridas: "Monofocal", "Bifocal", "Progresivo"
    @Column(name = "tipo_lente", length = 50)
    private String tipoLente;

    // Radio Button en Android
    // Opciones sugeridas: "Blanco" (Transparente), "Fotocromático" (Oscurece con sol)
    @Column(name = "tipo_cristal", length = 50)
    private String tipoCristal;

    // --- Tratamientos Adicionales (Checkboxes) ---
    
    // Checkbox (Sí/No)
    @Column(name = "antirreflejo")
    private Boolean antirreflejo;

    // Checkbox (Sí/No)
    @Column(name = "filtro_azul")
    private Boolean filtroAzul;

    // Valor informativo calculado en la App
    @Column(name = "valor_aprox", precision = 10, scale = 2)
    private BigDecimal valorAprox;
}