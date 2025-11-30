package cl.duoc.visso.controller;

import cl.duoc.visso.model.Usuario;
import cl.duoc.visso.service.UsuarioService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Ver datos del perfil
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPerfil(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Modificar datos del perfil
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPerfil(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            return usuarioService.actualizarUsuario(id, usuario)
                    .map(u -> ResponseEntity.ok(u))
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 3. Listar todos los usuarios (ADMIN - FALTABA ESTE)
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        // Nota: Si no tienes este método en el servicio, el repositorio JPA lo tiene por defecto (findAll)
        // Aquí asumimos que tu servicio expone listarTodos() o similar.
        // Si da error, usa usuarioRepository.findAll() directamente si tienes acceso, o agrega el método al servicio.
        return ResponseEntity.ok(usuarioService.listarTodos()); 
    }

    // Crear nuevo usuario (ADMIN)
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
            return ResponseEntity.status(201).body(nuevoUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 4. Eliminar usuario (ADMIN - FALTABA ESTE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }    
}