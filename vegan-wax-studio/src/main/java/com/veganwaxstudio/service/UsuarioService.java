package com.veganwaxstudio.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.veganwaxstudio.entity.Usuario;
import com.veganwaxstudio.repository.UsuarioRepository;

// Servicio encargado de gestionar las operaciones relacionadas con los usuarios y la verificación de sus credenciales.
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor
    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Busca un usuario por su nombre de usuario
    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    // Comprueba las credenciales de acceso
    public boolean verificarCredenciales(String nombreUsuario, String password) {

        Optional<Usuario> usuarioEncontrado = buscarPorNombreUsuario(nombreUsuario);

        if (usuarioEncontrado.isEmpty()) {
            return false;
        }

        Usuario usuario = usuarioEncontrado.get();

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            return false;
        }

        return passwordEncoder.matches(password, usuario.getPasswordHash());
    }
}