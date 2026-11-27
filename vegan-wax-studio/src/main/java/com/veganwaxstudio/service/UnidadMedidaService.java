package com.veganwaxstudio.service;

import com.veganwaxstudio.entity.UnidadMedida;
import com.veganwaxstudio.repository.UnidadMedidaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Servicio encargado de consultar y validar las unidades de medida de los materiales.
@Service
@Transactional
public class UnidadMedidaService {

    private final UnidadMedidaRepository unidadMedidaRepository;

    // Constructor que permite inyectar el repositorio de unidades de medida.
    public UnidadMedidaService(UnidadMedidaRepository unidadMedidaRepository) {
        this.unidadMedidaRepository = unidadMedidaRepository;
    }

    // Busca una unidad de medida por su identificador y comprueba que exista y esté activa.
    @Transactional(readOnly = true)
    public UnidadMedida buscarUnidadActiva(Long id) {

        UnidadMedida unidad = unidadMedidaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "La unidad de medida indicada no existe"));

        if (!Boolean.TRUE.equals(unidad.getActivo())) {
            throw new IllegalArgumentException(
                    "No se pueden utilizar unidades de medida inactivas");
        }

        return unidad;
    }
}
