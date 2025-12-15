package cl.duoc.visso.service;

import cl.duoc.visso.model.Usuario;
import cl.duoc.visso.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder encoder;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    public Usuario registrar(Usuario usuario) {
        // Validaciones de negocio (duplicados en BD)
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        if (usuarioRepository.existsByRut(usuario.getRut())) {
            throw new RuntimeException("El RUT ya está registrado");
        }
        usuario.setPasswordHash(encoder.encode(usuario.getPasswordHash()));
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setActivo(true);
        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("usuario");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario login(String email, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (!usuarioOpt.isPresent()) {
            throw new RuntimeException("El correo no está registrado");
        }
        Usuario usuario = usuarioOpt.get();
        // Validar que el usuario esté activo
        if (usuario.getActivo() == null || !usuario.getActivo()) {
            throw new RuntimeException("Usuario inactivo. Contacte al administrador");
        }
        // Validar contraseña
        if (!encoder.matches(password, usuario.getPasswordHash())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        return usuario;
    }

    public boolean existeCorreo(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public boolean recuperarContrasena(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Le ponemos una clave dura "visso1234" encriptada
            usuario.setPasswordHash(encoder.encode("visso1234"));
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }
}