package com.veganwaxstudio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Controlador temporal para comprobar los permisos de acceso según el perfil del usuario.
@RestController
@RequestMapping("/api/productos/prueba")
public class PruebaController {

    @GetMapping("/consulta")
    public String consultarProductos() {
        return "Acceso permitido a la consulta de productos.";
    }

    @PostMapping("/gestion")
    public String gestionarProductos() {
        return "Acceso permitido a la gestion de productos.";
    }
}