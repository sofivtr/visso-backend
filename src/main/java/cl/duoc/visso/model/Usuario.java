package cl.duoc.visso.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Schema(name = "Usuario", description = "Usuario del sistema")
@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    @Schema(description = "Identificador único del usuario", example = "3")
    private Long id;

    @NotBlank
    @Column(name = "nombre", nullable = false)
    @Schema(description = "Nombre del usuario", example = "Juan")
    private String nombre;

    @NotBlank
    @Column(name = "apellido", nullable = false)
    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String apellido;

    @NotBlank
    @Column(name = "rut", nullable = false, unique = true)
    @Schema(description = "RUT del usuario", example = "12345678-9")
    private String rut;

    @Email
    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    @Schema(description = "Correo electrónico", example = "juan.perez@example.com")
    private String email;

    @Column(name = "password_hash", nullable = false)
    @Schema(description = "Hash de la contraseña")
    private String passwordHash;

    @Column(name = "rol")
    @Schema(description = "Rol del usuario", example = "usuario")
    private String rol;

    @Column(name = "fecha_registro")
    @Schema(description = "Fecha de registro", example = "2025-11-18T12:34:56")
    private LocalDateTime fechaRegistro;

    @Column(name = "activo")
    @Schema(description = "Indica si el usuario está activo", example = "true")
    private Boolean activo;
}
