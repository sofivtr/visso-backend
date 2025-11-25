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
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        usuario.setPasswordHash(encoder.encode(usuario.getPasswordHash()));
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setActivo(true);
        if (usuario.getRol() == null) usuario.setRol("USER");

        return usuarioRepository.save(usuario);
    }

    public Usuario login(String email, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (encoder.matches(password, usuario.getPasswordHash())) {
                return usuario;
            }
        }
        return null;
    }
}