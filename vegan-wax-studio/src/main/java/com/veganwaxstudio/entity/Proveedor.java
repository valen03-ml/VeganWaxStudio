
package com.veganwaxstudio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Entidad que representa a los proveedores que suministran materiales e insumos al centro.
@Entity
@Table(name = "proveedor")
public class Proveedor {

    // Identificador único del proveedor, generado automáticamente por la base de datos.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre obligatorio del proveedor, con un máximo de 100 caracteres y sin duplicados.
    @NotBlank(message = "El nombre del proveedor es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    // Indica si el proveedor está activo y puede asociarse a nuevos materiales.
    @Column(nullable = false)
    private Boolean activo = true;

    // Constructor vacío necesario para que JPA pueda crear instancias de la entidad.
    public Proveedor() {
    }

    // Devuelve el identificador del proveedor.
    public Long getId() {
        return id;
    }

    // Establece el identificador del proveedor.
    public void setId(Long id) {
        this.id = id;
    }

    // Devuelve el nombre del proveedor.
    public String getNombre() {
        return nombre;
    }

    // Establece el nombre del proveedor.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Devuelve el estado de actividad del proveedor.
    public Boolean getActivo() {
        return activo;
    }

    // Establece el estado de actividad del proveedor.
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
