package cl.duoc.visso.service;

import cl.duoc.visso.model.Mensaje;
import cl.duoc.visso.repository.MensajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensajeService {

    private final MensajeRepository mensajeRepository;

    public MensajeService(MensajeRepository mensajeRepository) {
        this.mensajeRepository = mensajeRepository;
    }

    /**
     * Guardar un nuevo mensaje de contacto
     */
    @Transactional
    public Mensaje guardarMensaje(Mensaje mensaje) {
        mensaje.setFechaCreacion(LocalDateTime.now());
        mensaje.setEstado("PENDIENTE");
        return mensajeRepository.save(mensaje);
    }

    /**
     * Listar todos los mensajes ordenados por fecha descendente
     */
    public List<Mensaje> listarTodos() {
        return mensajeRepository.findAllByOrderByFechaCreacionDesc();
    }

    /**
     * Listar mensajes por estado
     */
    public List<Mensaje> listarPorEstado(String estado) {
        return mensajeRepository.findByEstado(estado);
    }

    /**
     * Cambiar el estado de un mensaje
     */
    @Transactional
    public Mensaje cambiarEstado(Long id, String nuevoEstado) {
        Mensaje mensaje = mensajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));
        mensaje.setEstado(nuevoEstado);
        return mensajeRepository.save(mensaje);
    }

    /**
     * Obtener un mensaje por ID
     */
    public Mensaje obtenerPorId(Long id) {
        return mensajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));
    }
}
