package cl.duoc.visso.service;

import cl.duoc.visso.model.Usuario;
import cl.duoc.visso.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder encoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> actualizarUsuario(Long id, Usuario usuarioEditado) {
        return usuarioRepository.findById(id).map(usuarioExistente -> {
            // Actualizar datos permitidos
            if (usuarioEditado.getNombre() != null) {
                usuarioExistente.setNombre(usuarioEditado.getNombre());
            }
            if (usuarioEditado.getApellido() != null) {
                usuarioExistente.setApellido(usuarioEditado.getApellido());
            }
            if (usuarioEditado.getEmail() != null) {
                // Validar que el email no esté en uso por otro usuario
                Optional<Usuario> existente = usuarioRepository.findByEmail(usuarioEditado.getEmail());
                if (existente.isPresent() && !existente.get().getId().equals(id)) {
                    throw new RuntimeException("El email ya está en uso");
                }
                usuarioExistente.setEmail(usuarioEditado.getEmail());
            }
            if (usuarioEditado.getRol() != null) {
                usuarioExistente.setRol(usuarioEditado.getRol());
            }
            if (usuarioEditado.getActivo() != null && usuarioEditado.getActivo() != usuarioExistente.getActivo()) {
                usuarioExistente.setActivo(usuarioEditado.getActivo());
            }
            // Si se envía un nuevo password (solo admin puede cambiarlo)
            if (usuarioEditado.getPasswordHash() != null && !usuarioEditado.getPasswordHash().isEmpty()) {
                if (usuarioEditado.getPasswordHash().length() < 8) {
                    throw new RuntimeException("La contraseña debe tener al menos 8 caracteres");
                }
                usuarioExistente.setPasswordHash(encoder.encode(usuarioEditado.getPasswordHash()));
            }
            
            return usuarioRepository.save(usuarioExistente);
        });
    }

    // --- MÉTODOS NUEVOS PARA EL ADMIN ---

    // 1. Listar todos los usuarios
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // 2. Crear nuevo usuario
    public Usuario crearUsuario(Usuario usuario) {
        // Validaciones de negocio (duplicados en BD)
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }
        if (usuarioRepository.findByRut(usuario.getRut()).isPresent()) {
            throw new RuntimeException("El RUT ya está registrado");
        }
        // Encriptar password
        usuario.setPasswordHash(encoder.encode(usuario.getPasswordHash()));
        usuario.setFechaRegistro(LocalDateTime.now());
        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("usuario");
        }
        if (usuario.getActivo() == null) {
            usuario.setActivo(true);
        }
        return usuarioRepository.save(usuario);
    }

    // 3. Eliminar un usuario por ID
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}