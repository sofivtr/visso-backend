package cl.duoc.visso.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RecuperarPasswordRequest", description = "Solicitud para recuperar contraseña")
public class RecuperarPasswordRequest {
    @Schema(description = "Correo electrónico registrado", example = "juan.perez@example.com")
    private String email;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

