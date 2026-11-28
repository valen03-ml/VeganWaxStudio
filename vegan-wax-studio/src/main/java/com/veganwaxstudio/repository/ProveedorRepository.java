
package com.veganwaxstudio.repository;

import com.veganwaxstudio.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repositorio encargado de las operaciones de acceso a datos de los proveedores.
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    // Comprueba si ya existe un proveedor con el nombre indicado, ignorando mayúsculas y minúsculas.
    boolean existsByNombreIgnoreCase(String nombre);

    // Busca un proveedor por su nombre, ignorando mayúsculas y minúsculas.
    Optional<Proveedor> findByNombreIgnoreCase(String nombre);
}
