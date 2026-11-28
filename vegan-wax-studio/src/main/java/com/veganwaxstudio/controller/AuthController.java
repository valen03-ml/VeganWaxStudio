
package com.veganwaxstudio.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.veganwaxstudio.entity.Usuario;
import com.veganwaxstudio.service.UsuarioService;

// Controlador encargado de gestionar el inicio y cierre de sesión.
@RestController
@RequestMapping("/api/auth")
public class AuthController {

        private final UsuarioService usuarioService;

        // Constructor
        public AuthController(UsuarioService usuarioService) {
                this.usuarioService = usuarioService;
        }

        // Permite iniciar sesión mediante las credenciales
        @PostMapping("/login")
        public ResponseEntity<?> iniciarSesion(
                @RequestBody LoginRequest datos,
                HttpServletRequest request) {

                // Comprueba que se han introducido los campos obligatorios
                if (datos.nombreUsuario() == null || datos.nombreUsuario().isBlank()
                        || datos.password() == null || datos.password().isBlank()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "El nombre de usuario y la contraseña son obligatorios.");
                }

                // Verifica las credenciales
                boolean credencialesCorrectas = usuarioService.verificarCredenciales(
                        datos.nombreUsuario(),
                        datos.password());

                if (!credencialesCorrectas) {
                        throw new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Nombre de usuario o contraseña incorrectos.");
                }

                // Recupera los datos del usuario autenticado
                Usuario usuario = usuarioService
                        .buscarPorNombreUsuario(datos.nombreUsuario())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED));

                // Crea o recupera la sesión y cambia su identificador
                HttpSession session = request.getSession();
                request.changeSessionId();

                // Guarda los datos necesarios en la sesión
                session.setAttribute("usuarioId", usuario.getId());
                session.setAttribute("nombreUsuario", usuario.getNombreUsuario());
                session.setAttribute("perfil", usuario.getPerfil().getNombre());

                // Devuelve una respuesta sin exponer la contraseña
                return ResponseEntity.ok(
                        "Inicio de sesion correcto.\n" +
                        "Nombre de usuario: " + usuario.getNombreUsuario() + "\n" +
                        "Perfil: " + usuario.getPerfil().getNombre());
        }

        // Permite cerrar la sesión del usuario
        @PostMapping("/logout")
        public ResponseEntity<?> cerrarSesion(HttpServletRequest request) {

                // Recupera la sesión actual sin crear una nueva
                HttpSession session = request.getSession(false);

                // Comprueba que existe una sesión activa
                if (session == null) {
                        throw new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "No hay ninguna sesion activa.");
                }

                // Invalida la sesión actual
                session.invalidate();

                return ResponseEntity.ok(
                        "Sesion cerrada correctamente.");
        }

        // Representa los datos recibidos para iniciar sesión
        public record LoginRequest(String nombreUsuario, String password) {
        }
}
