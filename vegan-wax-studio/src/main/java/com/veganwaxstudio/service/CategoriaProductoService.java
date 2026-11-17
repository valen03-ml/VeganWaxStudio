
package com.veganwaxstudio.service;

import com.veganwaxstudio.entity.CategoriaProducto;
import com.veganwaxstudio.repository.CategoriaProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

// Servicio que contiene la lógica de gestión de categorías de productos.
@Service
public class CategoriaProductoService {

    // Repositorio utilizado para acceder a las categorías almacenadas en MySQL.
    private final CategoriaProductoRepository categoriaRepository;

    // Constructor para inyectar el repositorio.
    public CategoriaProductoService(CategoriaProductoRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // Registra una nueva categoría después de comprobar que su nombre no esté duplicado.
    @Transactional
    public CategoriaProducto registrar(CategoriaProducto categoria) {

        // Elimina los espacios sobrantes al principio y al final del nombre.
        String nombre = categoria.getNombre().trim();

        // Comprueba que no exista otra categoría con el mismo nombre.
        if (categoriaRepository.existsByNombreIgnoreCase(nombre)) {
            throw new IllegalArgumentException(
                "Ya existe una categoría con ese nombre"
            );
        }

        // Guarda el nombre limpio y establece la categoría como activa.
        categoria.setNombre(nombre);
        categoria.setActivo(true);

        // Persiste la nueva categoría en la base de datos.
        return categoriaRepository.save(categoria);
    }

    // Consulta todas las categorías registradas, tanto activas como inactivas.
    @Transactional(readOnly = true)
    public List<CategoriaProducto> consultarTodas() {

        // Recupera todas las categorías almacenadas en la base de datos.
        return categoriaRepository.findAll();
    }

    // Modifica el nombre y la descripción de una categoría existente.
    @Transactional
    public CategoriaProducto modificar(Long id, CategoriaProducto datosNuevos) {

        // Busca la categoría que se desea modificar.
        CategoriaProducto categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe una categoría con ese identificador"
                ));

        // Elimina los espacios sobrantes del nuevo nombre.
        String nombre = datosNuevos.getNombre().trim();

        // Busca si existe otra categoría con el mismo nombre.
        CategoriaProducto existente = categoriaRepository
                .findByNombreIgnoreCase(nombre)
                .orElse(null);

        // Impide utilizar el nombre de otra categoría.
        if (existente != null && !existente.getId().equals(id)) {
            throw new IllegalArgumentException(
                    "Ya existe otra categoría con ese nombre"
            );
        }

        // Actualiza el nombre y la descripción.
        categoria.setNombre(nombre);
        categoria.setDescripcion(datosNuevos.getDescripcion().trim());

        // Guarda los cambios sin modificar el estado activo de la categoría.
        return categoriaRepository.save(categoria);
    }

    // Desactiva una categoría sin eliminarla de la base de datos.
    @Transactional
    public CategoriaProducto desactivar(Long id) {

        // Busca la categoría que se desea desactivar.
        CategoriaProducto categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe una categoría con ese identificador"
                ));

        // Comprueba que la categoría esté activa.
        if (!categoria.getActivo()) {
            throw new IllegalArgumentException(
                    "La categoría ya está desactivada"
            );
        }

        // Cambia su estado a inactivo.
        categoria.setActivo(false);

        // Guarda el cambio sin eliminar la información de la categoría.
        return categoriaRepository.save(categoria);
    }

    // Reactiva una categoría que estaba desactivada.
    @Transactional
    public CategoriaProducto reactivar(Long id) {

        // Busca la categoría que se desea reactivar.
        CategoriaProducto categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe una categoría con ese identificador"
                ));

        // Comprueba que la categoría esté desactivada.
        if (categoria.getActivo()) {
            throw new IllegalArgumentException(
                    "La categoría ya está activa"
            );
        }

        // Cambia su estado a activo.
        categoria.setActivo(true);

        // Guarda el cambio en la base de datos.
        return categoriaRepository.save(categoria);
    }
}
