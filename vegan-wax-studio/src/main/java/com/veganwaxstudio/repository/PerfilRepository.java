package com.veganwaxstudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.veganwaxstudio.entity.Perfil;
import java.util.Optional;

//Repositorio para gestionar las operaciones de acceso a los datos de los perfiles de usuario.
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    Optional<Perfil> findByNombre(String nombre);

}