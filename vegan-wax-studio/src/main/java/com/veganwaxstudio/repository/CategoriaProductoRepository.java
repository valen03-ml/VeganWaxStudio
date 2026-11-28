
package com.veganwaxstudio.repository;

import com.veganwaxstudio.entity.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repositorio encargado del acceso a los datos de las categorías de productos.
public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Long> {

    // Comprueba si existe una categoría con el mismo nombre,
    // sin distinguir entre mayúsculas y minúsculas.
    boolean existsByNombreIgnoreCase(String nombre);

    // Busca una categoría por su nombre, ignorando mayúsculas y minúsculas.
    Optional<CategoriaProducto> findByNombreIgnoreCase(String nombre);
}
