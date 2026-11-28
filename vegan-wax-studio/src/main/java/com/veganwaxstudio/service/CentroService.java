
package com.veganwaxstudio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.veganwaxstudio.entity.Centro;
import com.veganwaxstudio.repository.CentroRepository;

//Servicio encargado de gestionar los datos generales del centro.

@Service
public class CentroService {

    private final CentroRepository centroRepository;

    // Constructor
    public CentroService(CentroRepository centroRepository) {
        this.centroRepository = centroRepository;
    }

    // Consulta los datos del centro
    @Transactional(readOnly = true)
    public Centro consultarDatosCentro() {
        List<Centro> centros = centroRepository.findAll();

        if (centros.isEmpty()) {
            return null;
        }

        return centros.get(0);
    }

    // Registra los datos del centro
    @Transactional
    public Centro registrarDatosCentro(Centro centro) {

        if (centroRepository.count() > 0) {
            throw new IllegalStateException(
                    "Los datos del centro ya están registrados.");
        }

        return centroRepository.save(centro);
    }

    // Modifica los datos del centro
    @Transactional
    public Centro modificarDatosCentro(Centro datosActualizados) {

        Centro centroExistente = consultarDatosCentro();

        if (centroExistente == null) {
            throw new IllegalStateException(
                    "No existen datos del centro para modificar.");
        }

        centroExistente.setNombre(datosActualizados.getNombre());
        centroExistente.setDireccion(datosActualizados.getDireccion());
        centroExistente.setTelefono(datosActualizados.getTelefono());
        centroExistente.setCorreoElectronico(
                datosActualizados.getCorreoElectronico());
        centroExistente.setDatosFiscales(datosActualizados.getDatosFiscales());

        return centroRepository.save(centroExistente);
    }
}
