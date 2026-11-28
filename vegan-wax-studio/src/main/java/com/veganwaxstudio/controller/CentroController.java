
package com.veganwaxstudio.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.veganwaxstudio.entity.Centro;
import com.veganwaxstudio.service.CentroService;

import jakarta.validation.Valid;

//Controlador encargado de gestionar los datos generales del centro.

@RestController
@RequestMapping("/api/centro")
public class CentroController {

    private final CentroService centroService;

    // Constructor
    public CentroController(CentroService centroService) {
        this.centroService = centroService;
    }

    // Consulta los datos generales del centro
    @GetMapping
    public ResponseEntity<Centro> consultarDatosCentro() {

        Centro centro = centroService.consultarDatosCentro();

        if (centro == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(centro);
    }

    // Registra los datos generales del centro
    @PostMapping
    public ResponseEntity<Centro> registrarDatosCentro(
            @Valid @RequestBody Centro centro) {

        try {
            Centro centroRegistrado =
                    centroService.registrarDatosCentro(centro);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(centroRegistrado);

        } catch (IllegalStateException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, e.getMessage());
        }
    }

    // Modifica los datos generales del centro
    @PutMapping
    public ResponseEntity<Centro> modificarDatosCentro(
            @Valid @RequestBody Centro centro) {

        try {
            Centro centroModificado =
                    centroService.modificarDatosCentro(centro);

            return ResponseEntity.ok(centroModificado);

        } catch (IllegalStateException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
