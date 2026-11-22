
package com.veganwaxstudio.service;

import com.veganwaxstudio.entity.CategoriaProducto;
import com.veganwaxstudio.entity.Producto;
import com.veganwaxstudio.repository.CategoriaProductoRepository;
import com.veganwaxstudio.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Servicio que contiene la lógica de negocio para gestionar productos.
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaProductoRepository categoriaProductoRepository;

    // Constructor para inyectar los repositorios necesarios.
    public ProductoService(
            ProductoRepository productoRepository,
            CategoriaProductoRepository categoriaProductoRepository) {
        this.productoRepository = productoRepository;
        this.categoriaProductoRepository = categoriaProductoRepository;
    }

    // Registra un producto después de comprobar su referencia y categoría.
    @Transactional
    public Producto registrar(Producto producto) {

        // Elimina espacios al principio y al final de la referencia.
        producto.setCodigoReferencia(producto.getCodigoReferencia().trim());

        // Comprueba que no exista otro producto con la misma referencia.
        if (productoRepository.existsByCodigoReferenciaIgnoreCase(
                producto.getCodigoReferencia())) {
            throw new IllegalArgumentException(
                    "Ya existe un producto con ese código de referencia");
        }

        // Comprueba que la categoría indicada exista.
        Long categoriaId = producto.getCategoria().getId();

        CategoriaProducto categoria = categoriaProductoRepository.findById(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "La categoría indicada no existe"));

        // Impide asignar productos a categorías que estén desactivadas.
        if (!Boolean.TRUE.equals(categoria.getActivo())) {
            throw new IllegalArgumentException(
                    "No se pueden registrar productos en una categoría inactiva");
        }

        // Asocia al producto la categoría recuperada de la base de datos.
        producto.setCategoria(categoria);

        // Establece el producto como activo al registrarlo.
        producto.setActivo(true);

        // Guarda el producto y devuelve el registro persistido.
        return productoRepository.save(producto);
    }

    // Consulta todos los productos registrados, activos e inactivos.
    @Transactional(readOnly = true)
    public List<Producto> consultarTodos() {
        return productoRepository.findAll();
    }

    // Consulta únicamente los productos que están activos.
    @Transactional(readOnly = true)
    public List<Producto> consultarActivos() {
        return productoRepository.findAll()
                .stream()
                .filter(producto -> Boolean.TRUE.equals(producto.getActivo()))
                .toList();
    }

    // Modifica los datos de un producto existente.
    @Transactional
    public Producto modificar(Long id, Producto datosActualizados) {

        // Busca el producto que se desea modificar.
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El producto indicado no existe"));

        // Comprueba que el código de referencia no pertenezca a otro producto.
        if (productoRepository.existsByCodigoReferenciaIgnoreCaseAndIdNot(
                datosActualizados.getCodigoReferencia().trim(), id)) {
            throw new IllegalArgumentException("Ya existe otro producto con ese código de referencia");
        }

        // Busca la categoría indicada y comprueba que esté activa.
        Long categoriaId = datosActualizados.getCategoria().getId();
        CategoriaProducto categoria = categoriaProductoRepository.findById(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("La categoría indicada no existe"));

        if (!Boolean.TRUE.equals(categoria.getActivo())) {
            throw new IllegalArgumentException("No se pueden asignar productos a una categoría inactiva");
        }

        // Actualiza los datos del producto sin modificar sus existencias ni su estado.
        producto.setCodigoReferencia(datosActualizados.getCodigoReferencia().trim());
        producto.setNombre(datosActualizados.getNombre());
        producto.setDescripcion(datosActualizados.getDescripcion());
        producto.setMarca(datosActualizados.getMarca());
        producto.setPrecioVenta(datosActualizados.getPrecioVenta());
        producto.setCategoria(categoria);

        // Guarda los cambios en la base de datos.
        return productoRepository.save(producto);
    }

    // Desactiva un producto sin eliminarlo de la base de datos.
    @Transactional
    public Producto desactivar(Long id) {

        // Busca el producto que se desea desactivar.
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El producto indicado no existe"));

        // Comprueba que el producto esté activo.
        if (!Boolean.TRUE.equals(producto.getActivo())) {
            throw new IllegalArgumentException("El producto ya está desactivado");
        }

        // Cambia su estado a inactivo y conserva toda su información.
        producto.setActivo(false);

        // Guarda el cambio en la base de datos.
        return productoRepository.save(producto);
    }

    // Reactiva un producto que estaba desactivado.
    @Transactional
    public Producto reactivar(Long id) {

        // Busca el producto que se desea reactivar.
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El producto indicado no existe"));

        // Comprueba que el producto esté desactivado.
        if (Boolean.TRUE.equals(producto.getActivo())) {
            throw new IllegalArgumentException("El producto ya está activo");
        }

        // Cambia su estado a activo.
        producto.setActivo(true);

        // Guarda el cambio en la base de datos.
        return productoRepository.save(producto);
    }
}
