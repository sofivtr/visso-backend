package cl.duoc.visso.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MensajeResponse", description = "Respuesta simple con mensaje")
public class MensajeResponse {
    @Schema(description = "Mensaje descriptivo de la operación", example = "Operación realizada con éxito")
    private String mensaje;

    public MensajeResponse() {}
    public MensajeResponse(String mensaje) { this.mensaje = mensaje; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}

