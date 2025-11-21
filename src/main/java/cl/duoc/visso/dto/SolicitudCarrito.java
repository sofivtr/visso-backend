package cl.duoc.visso.dto;

public class SolicitudCarrito {
    // Esta es la "cajita" para recibir los datos desde la App
    private Long usuarioId;
    private Long productoId;
    private Integer cantidad;

    // Getters y Setters (necesarios para que Spring guarde los datos aquí)
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}