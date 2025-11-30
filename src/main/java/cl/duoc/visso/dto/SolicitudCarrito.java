package cl.duoc.visso.dto;

public class SolicitudCarrito {
    private Long usuarioId;
    private Long productoId;
    private Integer cantidad;
    
    // Opcional: se envía solo si el producto es óptico y tiene cotización
    private Long cotizacionId; 

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Long getCotizacionId() { return cotizacionId; }
    public void setCotizacionId(Long cotizacionId) { this.cotizacionId = cotizacionId; }
}