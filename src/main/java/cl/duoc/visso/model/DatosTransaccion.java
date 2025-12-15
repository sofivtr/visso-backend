package cl.duoc.visso.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "DatosTransaccion", description = "Datos de envío y pago de una transacción")
@Entity
@Table(name = "datos_transaccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatosTransaccion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_datos_transaccion")
    @Schema(description = "Identificador único de los datos de transacción", example = "1")
    private Long id;
    
    @Column(name = "nombre_contacto", nullable = false)
    @Schema(description = "Nombre del contacto para la entrega", example = "Juan Pérez")
    private String nombreContacto;
    
    @Column(name = "telefono", nullable = false)
    @Schema(description = "Teléfono de contacto", example = "+56912345678")
    private String telefono;
    
    @Column(name = "email", nullable = false)
    @Schema(description = "Email de contacto", example = "juan@example.com")
    private String email;
    
    @Column(name = "region", nullable = false)
    @Schema(description = "Región de entrega", example = "Metropolitana")
    private String region;
    
    @Column(name = "comuna", nullable = false)
    @Schema(description = "Comuna de entrega", example = "Santiago")
    private String comuna;
    
    @Column(name = "direccion")
    @Schema(description = "Dirección de entrega (opcional si es retiro en tienda)", example = "Av. Providencia 123")
    private String direccion;
    
    @Column(name = "tipo_entrega", nullable = false)
    @Schema(description = "Tipo de entrega: RETIRO o DESPACHO", example = "DESPACHO", allowableValues = {"RETIRO", "DESPACHO"})
    private String tipoEntrega;
    
    @Column(name = "tipo_pago", nullable = false)
    @Schema(description = "Tipo de pago: DEBITO, CREDITO o TRANSFERENCIA", example = "CREDITO", allowableValues = {"DEBITO", "CREDITO", "TRANSFERENCIA"})
    private String tipoPago;
    
    @Column(name = "info_tarjeta")
    @Schema(description = "Últimos 4 dígitos de la tarjeta (formato: **** 1234)", example = "**** 1234")
    private String infoTarjeta;
}
