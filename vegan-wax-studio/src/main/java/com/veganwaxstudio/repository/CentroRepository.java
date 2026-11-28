
package com.veganwaxstudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.veganwaxstudio.entity.Centro;

// Repositorio para gestionar las operaciones de acceso a los datos generales del centro.
public interface CentroRepository extends JpaRepository<Centro, Long> {
}
