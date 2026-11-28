
package com.veganwaxstudio.controller;

import com.veganwaxstudio.entity.Material;
import com.veganwaxstudio.service.MaterialService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Controlador encargado de gestionar las operaciones de materiales e insumos.
@RestController
@RequestMapping("/api/materiales")
public class MaterialController {

    private final MaterialService materialService;

    // Constructor que permite inyectar el servicio de materiales.
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    // Registra un nuevo material o insumo.
    @PostMapping
    public ResponseEntity<Material> registrar(
            @Valid @RequestBody Material material) {

        Material materialRegistrado = materialService.registrar(material);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(materialRegistrado);
    }

    // Consulta todos los materiales, incluidos los que están inactivos.
    @GetMapping
    public ResponseEntity<List<Material>> consultarTodos() {
        return ResponseEntity.ok(materialService.consultarTodos());
    }

    // Consulta únicamente los materiales que están activos.
    @GetMapping("/activos")
    public ResponseEntity<List<Material>> consultarActivos() {
        return ResponseEntity.ok(materialService.consultarActivos());
    }

    // Modifica los datos de un material identificado por su ID.
    @PutMapping("/{id}")
    public ResponseEntity<Material> modificar(
            @PathVariable Long id,
            @Valid @RequestBody Material material) {

        Material materialModificado = materialService.modificar(id, material);

        return ResponseEntity.ok(materialModificado);
    }

    // Desactiva un material sin eliminar su información ni sus existencias.
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Material> desactivar(@PathVariable Long id) {

        Material materialDesactivado = materialService.desactivar(id);

        return ResponseEntity.ok(materialDesactivado);
    }

    // Reactiva un material que se encontraba desactivado.
    @PatchMapping("/{id}/reactivar")
    public ResponseEntity<Material> reactivar(@PathVariable Long id) {

        Material materialReactivado = materialService.reactivar(id);

        return ResponseEntity.ok(materialReactivado);
    }
}
