
package com.veganwaxstudio.repository;

import com.veganwaxstudio.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Repositorio encargado de realizar las operaciones de acceso a datos de los productos.
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Comprueba si ya existe un producto con el mismo código de referencia.
    boolean existsByCodigoReferenciaIgnoreCase(String codigoReferencia);

    // Busca un producto mediante su código de referencia, sin distinguir mayúsculas y minúsculas.
    Optional<Producto> findByCodigoReferenciaIgnoreCase(String codigoReferencia);

    // Comprueba si existe otro producto con el mismo código de referencia y distinto identificador.
    boolean existsByCodigoReferenciaIgnoreCaseAndIdNot(String codigoReferencia, Long id);

    // Comprueba si una categoría tiene productos activos asociados.
    boolean existsByCategoriaIdAndActivoTrue(Long categoriaId);
}
