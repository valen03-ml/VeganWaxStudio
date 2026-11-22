
package com.veganwaxstudio.controller;

import com.veganwaxstudio.entity.Producto;
import com.veganwaxstudio.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST que gestiona las peticiones relacionadas con los productos.
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    // Constructor para inyectar el servicio de productos.
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Registra un nuevo producto y devuelve el registro creado.
    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody Producto producto) {
        try {
            // Llama al servicio para validar y guardar el producto.
            Producto productoRegistrado = productoService.registrar(producto);

            // Devuelve el producto registrado con el código HTTP 201.
            return ResponseEntity.status(HttpStatus.CREATED).body(productoRegistrado);

        } catch (IllegalArgumentException e) {
            // Devuelve un conflicto cuando existe una restricción de negocio.
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Consulta todos los productos, incluidos los activos y los inactivos.
    @GetMapping
    public ResponseEntity<List<Producto>> consultarTodos() {
        return ResponseEntity.ok(productoService.consultarTodos());
    }

    // Consulta únicamente los productos activos disponibles para nuevas ventas.
    @GetMapping("/activos")
    public ResponseEntity<List<Producto>> consultarActivos() {
        return ResponseEntity.ok(productoService.consultarActivos());
    }

    // Modifica un producto existente y devuelve los datos actualizados.
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(
            @PathVariable Long id,
            @Valid @RequestBody Producto producto) {
        try {
            // Ejecuta la modificación del producto.
            Producto productoModificado = productoService.modificar(id, producto);

            // Devuelve el producto modificado con código HTTP 200.
            return ResponseEntity.ok(productoModificado);

        } catch (IllegalArgumentException e) {
            // Devuelve un conflicto cuando el producto o la categoría no son válidos.
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Desactiva un producto sin eliminarlo de la base de datos.
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        try {
            // Ejecuta la desactivación del producto.
            Producto productoDesactivado = productoService.desactivar(id);

            // Devuelve el producto desactivado.
            return ResponseEntity.ok(productoDesactivado);

        } catch (IllegalArgumentException e) {
            // Devuelve un conflicto si el producto no existe o ya está desactivado.
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Reactiva un producto que estaba desactivado.
    @PatchMapping("/{id}/reactivar")
    public ResponseEntity<?> reactivar(@PathVariable Long id) {
        try {
            // Ejecuta la reactivación del producto.
            Producto productoReactivado = productoService.reactivar(id);

            // Devuelve el producto reactivado.
            return ResponseEntity.ok(productoReactivado);

        } catch (IllegalArgumentException e) {
            // Devuelve un conflicto si el producto no existe o ya está activo.
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
