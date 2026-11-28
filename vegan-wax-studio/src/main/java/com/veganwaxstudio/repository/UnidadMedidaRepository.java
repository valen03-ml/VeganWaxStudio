
package com.veganwaxstudio.repository;

import com.veganwaxstudio.entity.UnidadMedida;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio encargado de las operaciones de acceso a datos de las unidades de medida.
public interface UnidadMedidaRepository extends JpaRepository<UnidadMedida, Long> {

    // Comprueba si ya existe una unidad de medida con el nombre indicado.
    boolean existsByNombreIgnoreCase(String nombre);

    // Comprueba si ya existe una unidad de medida con la abreviatura indicada.
    boolean existsByAbreviaturaIgnoreCase(String abreviatura);
}
