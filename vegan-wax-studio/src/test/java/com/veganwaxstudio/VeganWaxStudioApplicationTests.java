package com.veganwaxstudio;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.veganwaxstudio.entity.Usuario;
import com.veganwaxstudio.repository.UsuarioRepository;
import com.veganwaxstudio.service.UsuarioService;

// Pruebas unitarias para comprobar la verificación de credenciales.
@ExtendWith(MockitoExtension.class)
class VeganWaxStudioApplicationTests {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    // Prepara un usuario de prueba antes de cada test
    @BeforeEach
    void prepararUsuario() {
        usuario = new Usuario();
        usuario.setNombreUsuario("recepcionista");
        usuario.setPasswordHash("hash_de_prueba");
        usuario.setActivo(true);
    }

    // Comprueba que las credenciales correctas son aceptadas
    @Test
    void verificarCredencialesCorrectas() {

        when(usuarioRepository.findByNombreUsuario("recepcionista"))
                .thenReturn(Optional.of(usuario));

        when(passwordEncoder.matches("claveCorrecta", "hash_de_prueba"))
                .thenReturn(true);

        assertTrue(usuarioService.verificarCredenciales(
                "recepcionista", "claveCorrecta"));
    }

    // Comprueba que una contraseña incorrecta es rechazada
    @Test
    void rechazarPasswordIncorrecta() {

        when(usuarioRepository.findByNombreUsuario("recepcionista"))
                .thenReturn(Optional.of(usuario));

        when(passwordEncoder.matches("claveIncorrecta", "hash_de_prueba"))
                .thenReturn(false);

        assertFalse(usuarioService.verificarCredenciales(
                "recepcionista", "claveIncorrecta"));
    }

    // Comprueba que un usuario inexistente no puede acceder
    @Test
    void rechazarUsuarioInexistente() {

        when(usuarioRepository.findByNombreUsuario("desconocido"))
                .thenReturn(Optional.empty());

        assertFalse(usuarioService.verificarCredenciales(
                "desconocido", "cualquierClave"));
    }

    // Comprueba que un usuario desactivado no puede acceder
    @Test
    void rechazarUsuarioDesactivado() {

        usuario.setActivo(false);

        when(usuarioRepository.findByNombreUsuario("recepcionista"))
                .thenReturn(Optional.of(usuario));

        assertFalse(usuarioService.verificarCredenciales(
                "recepcionista", "claveCorrecta"));
    }
}