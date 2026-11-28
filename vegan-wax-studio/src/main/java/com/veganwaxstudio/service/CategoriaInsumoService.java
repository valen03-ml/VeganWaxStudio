package com.veganwaxstudio.service;

import com.veganwaxstudio.entity.CategoriaInsumo;
import com.veganwaxstudio.repository.CategoriaInsumoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Servicio encargado de gestionar las consultas y validaciones de las categorías de insumos.
@Service
@Transactional
public class CategoriaInsumoService {

    private final CategoriaInsumoRepository categoriaInsumoRepository;

    // Constructor que permite inyectar el repositorio de categorías de insumos.
    public CategoriaInsumoService(CategoriaInsumoRepository categoriaInsumoRepository) {
        this.categoriaInsumoRepository = categoriaInsumoRepository;
    }

    // Busca una categoría por su identificador y comprueba que exista y esté activa.
    @Transactional(readOnly = true)
    public CategoriaInsumo buscarCategoriaActiva(Long id) {

        CategoriaInsumo categoria = categoriaInsumoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "La categoría de insumos indicada no existe"));

        if (!Boolean.TRUE.equals(categoria.getActivo())) {
            throw new IllegalArgumentException(
                    "No se pueden utilizar categorías de insumos inactivas");
        }

        return categoria;
    }
}
