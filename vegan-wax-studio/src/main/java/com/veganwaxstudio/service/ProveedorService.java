package com.veganwaxstudio.service;

import com.veganwaxstudio.entity.Proveedor;
import com.veganwaxstudio.repository.ProveedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Servicio encargado de consultar y validar los proveedores de materiales.
@Service
@Transactional
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    // Constructor que permite inyectar el repositorio de proveedores.
    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    // Busca un proveedor por su identificador y comprueba que exista y esté activo.
    @Transactional(readOnly = true)
    public Proveedor buscarProveedorActivo(Long id) {

        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El proveedor indicado no existe"));

        if (!Boolean.TRUE.equals(proveedor.getActivo())) {
            throw new IllegalArgumentException(
                    "No se pueden utilizar proveedores inactivos");
        }

        return proveedor;
    }
}
