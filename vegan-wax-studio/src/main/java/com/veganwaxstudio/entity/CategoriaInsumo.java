
package com.veganwaxstudio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Entidad que representa las categorías utilizadas para clasificar los materiales e insumos del centro.
@Entity
@Table(name = "categoria_insumo")
public class CategoriaInsumo {

    // Identificador único de la categoría, generado automáticamente por la base de datos.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre obligatorio de la categoría, con un máximo de 100 caracteres y sin duplicados.
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    // Descripción obligatoria que permite detallar los materiales incluidos en la categoría.
    @NotBlank(message = "La descripción de la categoría es obligatoria")
    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    @Column(nullable = false, length = 255)
    private String descripcion;

    // Indica si la categoría está activa y puede utilizarse para registrar nuevos materiales.
    @Column(nullable = false)
    private Boolean activo = true;

    // Constructor vacío necesario para que JPA pueda crear instancias de la entidad.
    public CategoriaInsumo() {
    }

    // Devuelve el identificador de la categoría.
    public Long getId() {
        return id;
    }

    // Establece el identificador de la categoría.
    public void setId(Long id) {
        this.id = id;
    }

    // Devuelve el nombre de la categoría.
    public String getNombre() {
        return nombre;
    }

    // Establece el nombre de la categoría.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Devuelve la descripción de la categoría.
    public String getDescripcion() {
        return descripcion;
    }

    // Establece la descripción de la categoría.
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Devuelve el estado de actividad de la categoría.
    public Boolean getActivo() {
        return activo;
    }

    // Establece el estado de actividad de la categoría.
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
