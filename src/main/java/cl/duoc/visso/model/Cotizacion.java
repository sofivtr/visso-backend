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

    @Column(name = "nombre_paciente", nullable = false, length = 150)
    private String nombrePaciente;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @Column(name = "fecha_receta", nullable = false)
    private LocalDate fechaReceta;

    // Graduaciones (pueden ser decimales: -1.25, -2.50, etc.)
    @Column(name = "grado_od") // ojo derecho
    private Double gradoOd;

    @Column(name = "grado_oi") // ojo izquierdo
    private Double gradoOi;

    // Valor aproximado de la cotización (opcional, solo informativo)
    @Column(name = "valor_aprox", precision = 10, scale = 2)
    private BigDecimal valorAprox;

    // Dropdowns
    @Column(name = "tipo_lente", length = 50)
    private String tipoLente;      // monofocal, bifocal, progresivo

    @Column(name = "material_lente", length = 50)
    private String materialLente;  // policarbonato, cristal, etc.

    // Checkboxes
    @Column(name = "antirreflejo")
    private Boolean antirreflejo;

    @Column(name = "filtro_azul")
    private Boolean filtroAzul;

    @Column(name = "despacho_domicilio")
    private Boolean despachoDomicilio;

    // Radio buttons
    @Column(name = "tipo_cristal", length = 50)
    private String tipoCristal;    // blanco, transitions, fotocromático

    @Column(name = "tipo_marco", length = 50)
    private String tipoMarco;      // metálico, plástico, etc.
}
