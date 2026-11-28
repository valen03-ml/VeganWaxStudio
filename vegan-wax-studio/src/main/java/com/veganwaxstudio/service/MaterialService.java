package com.veganwaxstudio.service;

import com.veganwaxstudio.entity.Material;
import com.veganwaxstudio.repository.MaterialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Servicio encargado de gestionar las operaciones de negocio de los materiales e insumos.
@Service
@Transactional
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final CategoriaInsumoService categoriaInsumoService;
    private final ProveedorService proveedorService;
    private final UnidadMedidaService unidadMedidaService;

    // Constructor que permite inyectar los repositorios y servicios necesarios.
    public MaterialService(
            MaterialRepository materialRepository,
            CategoriaInsumoService categoriaInsumoService,
            ProveedorService proveedorService,
            UnidadMedidaService unidadMedidaService) {

        this.materialRepository = materialRepository;
        this.categoriaInsumoService = categoriaInsumoService;
        this.proveedorService = proveedorService;
        this.unidadMedidaService = unidadMedidaService;
    }

    // Registra un material después de comprobar sus datos y sus relaciones.
    public Material registrar(Material material) {

        if (material.getCategoria() == null
                || material.getCategoria().getId() == null) {
            throw new IllegalArgumentException(
                    "La categoría del material es obligatoria");
        }

        if (material.getProveedor() == null
                || material.getProveedor().getId() == null) {
            throw new IllegalArgumentException(
                    "El proveedor del material es obligatorio");
        }

        if (material.getUnidadMedida() == null
                || material.getUnidadMedida().getId() == null) {
            throw new IllegalArgumentException(
                    "La unidad de medida es obligatoria");
        }

        // Recupera las relaciones verificando que existan y estén activas.
        material.setCategoria(
                categoriaInsumoService.buscarCategoriaActiva(
                        material.getCategoria().getId()));

        material.setProveedor(
                proveedorService.buscarProveedorActivo(
                        material.getProveedor().getId()));

        material.setUnidadMedida(
                unidadMedidaService.buscarUnidadActiva(
                        material.getUnidadMedida().getId()));

        // Inicializa el estado del material como activo.
        material.setActivo(true);

        // Guarda el material en la base de datos.
        return materialRepository.save(material);
    }

    // Consulta todos los materiales, incluidos los inactivos.
    @Transactional(readOnly = true)
    public List<Material> consultarTodos() {
        return materialRepository.findAll();
    }

    // Consulta únicamente los materiales que están activos.
    @Transactional(readOnly = true)
    public List<Material> consultarActivos() {
        return materialRepository.findByActivoTrue();
    }

    // Modifica los datos de un material sin alterar sus existencias actuales.
    public Material modificar(Long id, Material datosActualizados) {

        // Busca el material que se desea modificar.
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El material indicado no existe"));

        // Comprueba que se hayan proporcionado las relaciones obligatorias.
        if (datosActualizados.getCategoria() == null
                || datosActualizados.getCategoria().getId() == null) {
            throw new IllegalArgumentException(
                    "La categoría del material es obligatoria");
        }

        if (datosActualizados.getProveedor() == null
                || datosActualizados.getProveedor().getId() == null) {
            throw new IllegalArgumentException(
                    "El proveedor del material es obligatorio");
        }

        if (datosActualizados.getUnidadMedida() == null
                || datosActualizados.getUnidadMedida().getId() == null) {
            throw new IllegalArgumentException(
                    "La unidad de medida es obligatoria");
        }

        // Comprueba que las relaciones existan y estén activas.
        material.setCategoria(
                categoriaInsumoService.buscarCategoriaActiva(
                        datosActualizados.getCategoria().getId()));

        material.setProveedor(
                proveedorService.buscarProveedorActivo(
                        datosActualizados.getProveedor().getId()));

        material.setUnidadMedida(
                unidadMedidaService.buscarUnidadActiva(
                        datosActualizados.getUnidadMedida().getId()));

        // Actualiza los datos descriptivos del material.
        material.setNombre(datosActualizados.getNombre());
        material.setDescripcion(datosActualizados.getDescripcion());
        material.setMarca(datosActualizados.getMarca());
        material.setFechaCaducidad(datosActualizados.getFechaCaducidad());

        // Conserva las existencias y el estado actual del material.
        return materialRepository.save(material);
    }

    // Desactiva un material sin eliminar su información ni sus existencias.
    public Material desactivar(Long id) {

        // Busca el material que se desea desactivar.
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El material indicado no existe"));

        // Comprueba que el material esté activo.
        if (!Boolean.TRUE.equals(material.getActivo())) {
            throw new IllegalArgumentException(
                    "El material ya está desactivado");
        }

        // Cambia su estado a inactivo.
        material.setActivo(false);

        // Guarda el cambio sin modificar las existencias.
        return materialRepository.save(material);
    }

    // Reactiva un material que se encontraba desactivado.
    public Material reactivar(Long id) {

        // Busca el material que se desea reactivar.
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El material indicado no existe"));

        // Comprueba que el material esté inactivo.
        if (Boolean.TRUE.equals(material.getActivo())) {
            throw new IllegalArgumentException(
                    "El material ya está activo");
        }

        // Cambia su estado a activo.
        material.setActivo(true);

        // Guarda el cambio.
        return materialRepository.save(material);
    }
}