
package com.veganwaxstudio.controller;

import com.veganwaxstudio.entity.CategoriaProducto;
import com.veganwaxstudio.service.CategoriaProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador que expone los endpoints para gestionar categorías de productos.
@RestController
@RequestMapping("/api/categorias-productos")
public class CategoriaProductoController {

    // Servicio encargado de ejecutar las operaciones de gestión.
    private final CategoriaProductoService categoriaService;

    // Constructor para inyectar el servicio.
    public CategoriaProductoController(CategoriaProductoService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Registra una nueva categoría mediante una petición POST.
    @PostMapping
    public ResponseEntity<?> registrar(
            @Valid @RequestBody CategoriaProducto categoria) {

        try {
            // Envía la categoría al servicio para validar y guardar sus datos.
            CategoriaProducto nueva = categoriaService.registrar(categoria);

            // Devuelve HTTP 201 (Created) si el registro se realiza correctamente.
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);

        } catch (IllegalArgumentException e) {

            // Devuelve HTTP 409 (Conflict) cuando el nombre ya está registrado.
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    // Consulta todas las categorías registradas mediante una petición GET.
    @GetMapping
    public ResponseEntity<List<CategoriaProducto>> consultarTodas() {

        // Recupera las categorías activas e inactivas desde el servicio.
        List<CategoriaProducto> categorias = categoriaService.consultarTodas();

        // Devuelve HTTP 200 (OK) con el listado de categorías.
        return ResponseEntity.ok(categorias);
    }

    // Modifica una categoría existente mediante una petición PUT.
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaProducto datosNuevos) {

        try {
            // Envía el identificador y los nuevos datos al servicio.
            CategoriaProducto categoriaModificada =
                    categoriaService.modificar(id, datosNuevos);

            // Devuelve HTTP 200 (OK) con la categoría modificada.
            return ResponseEntity.ok(categoriaModificada);

        } catch (IllegalArgumentException e) {

            // Devuelve HTTP 409 (Conflict) si hay un nombre duplicado
            // o la categoría no existe.
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    // Desactiva una categoría mediante una petición PATCH.
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {

        try {
            // Envía el identificador al servicio para desactivar la categoría.
            CategoriaProducto categoriaDesactivada =
                    categoriaService.desactivar(id);

            // Devuelve HTTP 200 (OK) con la categoría desactivada.
            return ResponseEntity.ok(categoriaDesactivada);

        } catch (IllegalArgumentException e) {

            // Devuelve HTTP 409 (Conflict) si no existe o ya está desactivada.
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    // Reactiva una categoría mediante una petición PATCH.
    @PatchMapping("/{id}/reactivar")
    public ResponseEntity<?> reactivar(@PathVariable Long id) {

        try {
            // Envía el identificador al servicio para reactivar la categoría.
            CategoriaProducto categoriaReactivada =
                    categoriaService.reactivar(id);

            // Devuelve HTTP 200 (OK) con la categoría reactivada.
            return ResponseEntity.ok(categoriaReactivada);

        } catch (IllegalArgumentException e) {

            // Devuelve HTTP 409 (Conflict) si no existe o ya está activa.
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }
}
