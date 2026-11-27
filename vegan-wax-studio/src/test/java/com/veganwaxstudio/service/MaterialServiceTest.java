package com.veganwaxstudio.service;

import com.veganwaxstudio.entity.CategoriaInsumo;
import com.veganwaxstudio.entity.Material;
import com.veganwaxstudio.entity.Proveedor;
import com.veganwaxstudio.entity.UnidadMedida;
import com.veganwaxstudio.repository.MaterialRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Pruebas automáticas del servicio encargado de gestionar los materiales e insumos.
@ExtendWith(MockitoExtension.class)
class MaterialServiceTest {

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private CategoriaInsumoService categoriaInsumoService;

    @Mock
    private ProveedorService proveedorService;

    @Mock
    private UnidadMedidaService unidadMedidaService;

    @InjectMocks
    private MaterialService materialService;

    private CategoriaInsumo categoria;
    private Proveedor proveedor;
    private UnidadMedida unidad;

    // Prepara los datos de referencia utilizados en las pruebas.
    @BeforeEach
    void prepararDatos() {
        categoria = new CategoriaInsumo();
        categoria.setId(1L);
        categoria.setActivo(true);

        proveedor = new Proveedor();
        proveedor.setId(1L);
        proveedor.setActivo(true);

        unidad = new UnidadMedida();
        unidad.setId(1L);
        unidad.setActivo(true);
    }

    // Comprueba que se registra correctamente un material con relaciones válidas.
    @Test
    void registrarMaterialCorrectamente() {
        Material material = crearMaterial();

        when(categoriaInsumoService.buscarCategoriaActiva(1L)).thenReturn(categoria);
        when(proveedorService.buscarProveedorActivo(1L)).thenReturn(proveedor);
        when(unidadMedidaService.buscarUnidadActiva(1L)).thenReturn(unidad);
        when(materialRepository.save(any(Material.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        Material resultado = materialService.registrar(material);

        assertNotNull(resultado);
        assertTrue(resultado.getActivo());
        assertSame(categoria, resultado.getCategoria());
        assertSame(proveedor, resultado.getProveedor());
        assertSame(unidad, resultado.getUnidadMedida());

        verify(materialRepository).save(material);
    }

    // Comprueba que no se registra un material cuando la categoría no es válida.
    @Test
    void rechazarCategoriaInvalida() {
        Material material = crearMaterial();

        when(categoriaInsumoService.buscarCategoriaActiva(1L))
                .thenThrow(new IllegalArgumentException("Categoría inactiva"));

        assertThrows(IllegalArgumentException.class,
                () -> materialService.registrar(material));

        verify(materialRepository, never()).save(any(Material.class));
    }

    // Comprueba que no se registra un material cuando el proveedor no es válido.
    @Test
    void rechazarProveedorInvalido() {
        Material material = crearMaterial();

        when(categoriaInsumoService.buscarCategoriaActiva(1L)).thenReturn(categoria);
        when(proveedorService.buscarProveedorActivo(1L))
                .thenThrow(new IllegalArgumentException("Proveedor inactivo"));

        assertThrows(IllegalArgumentException.class,
                () -> materialService.registrar(material));

        verify(materialRepository, never()).save(any(Material.class));
    }

    // Comprueba que no se registra un material cuando la unidad de medida no es válida.
    @Test
    void rechazarUnidadInvalida() {
        Material material = crearMaterial();

        when(categoriaInsumoService.buscarCategoriaActiva(1L)).thenReturn(categoria);
        when(proveedorService.buscarProveedorActivo(1L)).thenReturn(proveedor);
        when(unidadMedidaService.buscarUnidadActiva(1L))
                .thenThrow(new IllegalArgumentException("Unidad inactiva"));

        assertThrows(IllegalArgumentException.class,
                () -> materialService.registrar(material));

        verify(materialRepository, never()).save(any(Material.class));
    }

    // Comprueba que no se registra un material sin categoría.
    @Test
    void rechazarMaterialSinCategoria() {
        Material material = crearMaterial();
        material.setCategoria(null);

        assertThrows(IllegalArgumentException.class,
                () -> materialService.registrar(material));

        verifyNoInteractions(categoriaInsumoService, proveedorService,
                unidadMedidaService, materialRepository);
    }

    // Comprueba que no se registra un material sin proveedor.
    @Test
    void rechazarMaterialSinProveedor() {
        Material material = crearMaterial();
        material.setProveedor(null);

        assertThrows(IllegalArgumentException.class,
                () -> materialService.registrar(material));

        verify(materialRepository, never()).save(any(Material.class));
    }

    // Comprueba que no se registra un material sin unidad de medida.
    @Test
    void rechazarMaterialSinUnidad() {
        Material material = crearMaterial();
        material.setUnidadMedida(null);

        assertThrows(IllegalArgumentException.class,
                () -> materialService.registrar(material));

        verify(materialRepository, never()).save(any(Material.class));
    }

    // Comprueba que se modifican los datos del material y se conserva su stock.
    @Test
    void modificarMaterialCorrectamenteConservandoExistencias() {
        Material material = crearMaterial();
        material.setId(1L);
        material.setActivo(true);
        material.setCantidadDisponible(75);

        Material datosActualizados = crearMaterial();
        datosActualizados.setNombre("Cera vegana premium");
        datosActualizados.setDescripcion("Cera vegana profesional");

        when(materialRepository.findById(1L)).thenReturn(Optional.of(material));
        when(categoriaInsumoService.buscarCategoriaActiva(1L)).thenReturn(categoria);
        when(proveedorService.buscarProveedorActivo(1L)).thenReturn(proveedor);
        when(unidadMedidaService.buscarUnidadActiva(1L)).thenReturn(unidad);
        when(materialRepository.save(any(Material.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        Material resultado = materialService.modificar(1L, datosActualizados);

        assertEquals("Cera vegana premium", resultado.getNombre());
        assertEquals("Cera vegana profesional", resultado.getDescripcion());
        assertEquals(75, resultado.getCantidadDisponible());
        assertTrue(resultado.getActivo());

        verify(materialRepository).save(material);
    }

    // Comprueba que no se modifica un material que no existe.
    @Test
    void rechazarModificacionMaterialInexistente() {
        Material datosActualizados = crearMaterial();

        when(materialRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> materialService.modificar(99L, datosActualizados));

        verify(materialRepository, never()).save(any(Material.class));
    }

    // Comprueba que no se modifica un material sin categoría.
    @Test
    void rechazarModificacionSinCategoria() {
        Material material = crearMaterial();
        material.setId(1L);

        Material datosActualizados = crearMaterial();
        datosActualizados.setCategoria(null);

        when(materialRepository.findById(1L)).thenReturn(Optional.of(material));

        assertThrows(IllegalArgumentException.class,
                () -> materialService.modificar(1L, datosActualizados));

        verify(materialRepository, never()).save(any(Material.class));
    }

    // Comprueba que se desactiva un material conservando sus existencias.
    @Test
    void desactivarMaterialCorrectamenteConservandoExistencias() {
        Material material = crearMaterial();
        material.setId(1L);
        material.setActivo(true);
        material.setCantidadDisponible(75);

        when(materialRepository.findById(1L)).thenReturn(Optional.of(material));
        when(materialRepository.save(any(Material.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        Material resultado = materialService.desactivar(1L);

        assertFalse(resultado.getActivo());
        assertEquals(75, resultado.getCantidadDisponible());

        verify(materialRepository).save(material);
    }

    // Comprueba que no se desactiva un material que ya está inactivo.
    @Test
    void rechazarDesactivacionMaterialYaInactivo() {
        Material material = crearMaterial();
        material.setActivo(false);

        when(materialRepository.findById(1L)).thenReturn(Optional.of(material));

        assertThrows(IllegalArgumentException.class,
                () -> materialService.desactivar(1L));

        verify(materialRepository, never()).save(any(Material.class));
    }

    // Comprueba que no se desactiva un material que no existe.
    @Test
    void rechazarDesactivacionMaterialInexistente() {
        when(materialRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> materialService.desactivar(99L));

        verify(materialRepository, never()).save(any(Material.class));
    }

    // Comprueba que se reactiva un material conservando sus existencias.
    @Test
    void reactivarMaterialCorrectamenteConservandoExistencias() {
        Material material = crearMaterial();
        material.setId(1L);
        material.setActivo(false);
        material.setCantidadDisponible(75);

        when(materialRepository.findById(1L)).thenReturn(Optional.of(material));
        when(materialRepository.save(any(Material.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        Material resultado = materialService.reactivar(1L);

        assertTrue(resultado.getActivo());
        assertEquals(75, resultado.getCantidadDisponible());

        verify(materialRepository).save(material);
    }

    // Comprueba que no se reactiva un material que ya está activo.
    @Test
    void rechazarReactivacionMaterialYaActivo() {
        Material material = crearMaterial();
        material.setActivo(true);

        when(materialRepository.findById(1L)).thenReturn(Optional.of(material));

        assertThrows(IllegalArgumentException.class,
                () -> materialService.reactivar(1L));

        verify(materialRepository, never()).save(any(Material.class));
    }

    // Comprueba que no se reactiva un material que no existe.
    @Test
    void rechazarReactivacionMaterialInexistente() {
        when(materialRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> materialService.reactivar(99L));

        verify(materialRepository, never()).save(any(Material.class));
    }

    // Construye un material de prueba con datos válidos.
    private Material crearMaterial() {
        Material material = new Material();
        material.setNombre("Cera depilatoria");
        material.setDescripcion("Cera para tratamientos de depilación");
        material.setMarca("Marca de prueba");
        material.setCategoria(categoria);
        material.setProveedor(proveedor);
        material.setUnidadMedida(unidad);
        material.setCantidadDisponible(10);

        return material;
    }
}
