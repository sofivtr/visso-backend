package cl.duoc.visso.dto;

import cl.duoc.visso.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginResponse", description = "Respuesta del inicio de sesión")
public class LoginResponse {
    @Schema(description = "Token JWT de autenticación")
    private String token;

    @Schema(description = "Usuario autenticado")
    private Usuario usuario;

    public LoginResponse() {}
    public LoginResponse(String token, Usuario usuario) { this.token = token; this.usuario = usuario; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}

