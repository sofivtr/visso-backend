package cl.duoc.visso.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ActualizarCantidadRequest", description = "Solicitud para actualizar cantidad de un detalle del carrito")
public class ActualizarCantidadRequest {
    @Schema(description = "Nueva cantidad del detalle", example = "2")
    private Integer cantidad;

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}

