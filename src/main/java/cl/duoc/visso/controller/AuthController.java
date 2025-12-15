package cl.duoc.visso.controller;

import cl.duoc.visso.model.Usuario;
import cl.duoc.visso.config.JWTProveedor;
import cl.duoc.visso.service.AuthService;
import cl.duoc.visso.dto.LoginRequest;
import cl.duoc.visso.dto.LoginResponse;
import cl.duoc.visso.dto.RecuperarPasswordRequest;
import cl.duoc.visso.dto.MensajeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Auth")
public class AuthController {

    private final AuthService authService;
    private final JWTProveedor jwtTokenProvider;

    public AuthController(AuthService authService, JWTProveedor jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/registro")
    @Operation(summary = "Registrar usuario")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(authService.registrar(usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest credenciales) {
        try {
            Usuario usuario = authService.login(
                    credenciales.getEmail(),
                    credenciales.getPassword()
            );
            String token = jwtTokenProvider.generateToken(
                    usuario.getEmail(),
                    Map.of(
                        "id", usuario.getId(),
                        "rol", usuario.getRol(),
                        "nombre", usuario.getNombre()
                    )
            );
            return ResponseEntity.ok(new LoginResponse(token, usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/recuperar-password")
        @Operation(summary = "Recuperar contraseña")
        public ResponseEntity<MensajeResponse> recuperarPassword(@RequestBody RecuperarPasswordRequest body) {
            String email = body.getEmail();
            
            if (email == null || email.isEmpty()) {
                return ResponseEntity.badRequest().body(new MensajeResponse("Debe ingresar un correo."));
            }

            // Buscamos al usuario real
            // OJO: Tienes que agregar 'buscarPorEmail' en AuthService o usar el repo aquí.
            // Como ya tienes 'usuarioRepository' inyectado en AuthService, agreguemos un método allá.
            
            boolean resultado = authService.recuperarContrasena(email); // <--- Método nuevo que haremos abajo

            if (resultado) {
                return ResponseEntity.ok(new MensajeResponse("Clave restablecida. Tu nueva contraseña temporal es: visso1234"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeResponse("El correo no se encuentra registrado."));
            }
        }
}
