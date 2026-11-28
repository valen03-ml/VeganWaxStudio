package com.veganwaxstudio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veganwaxstudio.entity.Usuario;

// Repositorio para gestionar las operaciones de acceso a los datos de los usuarios del sistema.

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

}