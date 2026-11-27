package com.veganwaxstudio.repository;

import com.veganwaxstudio.entity.CategoriaInsumo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repositorio encargado de las operaciones de acceso a datos de las categorías de insumos.
public interface CategoriaInsumoRepository extends JpaRepository<CategoriaInsumo, Long> {

    // Comprueba si ya existe una categoría con el nombre indicado, ignorando mayúsculas y minúsculas.
    boolean existsByNombreIgnoreCase(String nombre);

    // Busca una categoría por su nombre, ignorando mayúsculas y minúsculas.
    Optional<CategoriaInsumo> findByNombreIgnoreCase(String nombre);
}
