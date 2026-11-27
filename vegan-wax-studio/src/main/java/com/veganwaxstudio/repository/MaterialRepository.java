
package com.veganwaxstudio.repository;

import com.veganwaxstudio.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repositorio encargado de las operaciones de acceso a datos de los materiales e insumos.
public interface MaterialRepository extends JpaRepository<Material, Long> {

    // Consulta los materiales que están activos.
    List<Material> findByActivoTrue();

    // Comprueba si existen materiales activos asociados a una categoría.
    boolean existsByCategoriaIdAndActivoTrue(Long categoriaId);

    // Comprueba si existen materiales activos asociados a un proveedor.
    boolean existsByProveedorIdAndActivoTrue(Long proveedorId);

    // Comprueba si existen materiales activos asociados a una unidad de medida.
    boolean existsByUnidadMedidaIdAndActivoTrue(Long unidadMedidaId);
}
