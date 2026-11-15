
package com.veganwaxstudio.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.veganwaxstudio.entity.Perfil;
import com.veganwaxstudio.entity.Usuario;
import com.veganwaxstudio.repository.PerfilRepository;
import com.veganwaxstudio.repository.UsuarioRepository;

// Clase encargada de inicializar los usuarios de prueba al arrancar la aplicación, si todavía no existen.
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner inicializarUsuarios(
            PerfilRepository perfilRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            Environment environment) {

        return args -> {

            // Creación de los usuarios iniciales para cada perfil
            crearUsuarioSiNoExiste(
                    "recepcionista",
                    "Recepcionista de prueba",
                    "INITIAL_PASSWORD_RECEPCIONISTA",
                    "Recepcionista",
                    perfilRepository,
                    usuarioRepository,
                    passwordEncoder,
                    environment);

            crearUsuarioSiNoExiste(
                    "encargado",
                    "Encargado de prueba",
                    "INITIAL_PASSWORD_ENCARGADO",
                    "Encargado",
                    perfilRepository,
                    usuarioRepository,
                    passwordEncoder,
                    environment);

            crearUsuarioSiNoExiste(
                    "jefe",
                    "Jefe de prueba",
                    "INITIAL_PASSWORD_JEFE",
                    "Jefe",
                    perfilRepository,
                    usuarioRepository,
                    passwordEncoder,
                    environment);
        };
    }

    // Comprueba si el usuario existe y, si no, lo registra
    private void crearUsuarioSiNoExiste(
            String nombreUsuario,
            String nombreCompleto,
            String variablePassword,
            String nombrePerfil,
            PerfilRepository perfilRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            Environment environment) {

        // Evita duplicar usuarios en la base de datos
        if (usuarioRepository.findByNombreUsuario(nombreUsuario).isPresent()) {
            return;
        }

        // Recupera la contraseña desde la configuración de Spring
        String password = environment.getProperty(variablePassword);

        if (password == null || password.isBlank()) {
            throw new IllegalStateException(
                    "No se ha configurado la variable " + variablePassword);
        }

        // Busca el perfil asociado al usuario
        Perfil perfil = perfilRepository.findByNombre(nombrePerfil)
                .orElseThrow(() -> new IllegalStateException(
                        "No existe el perfil: " + nombrePerfil));

        // Crea el usuario y almacena su contraseña mediante BCrypt
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setNombreCompleto(nombreCompleto);
        usuario.setPasswordHash(passwordEncoder.encode(password));
        usuario.setPerfil(perfil);
        usuario.setActivo(true);

        usuarioRepository.save(usuario);
    }
}
