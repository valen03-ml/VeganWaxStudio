
package com.veganwaxstudio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Entidad que representa las unidades de medida utilizadas para controlar las existencias de los materiales.
@Entity
@Table(name = "unidad_medida")
public class UnidadMedida {

    // Identificador único de la unidad de medida, generado automáticamente por la base de datos.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre obligatorio de la unidad de medida, con un máximo de 50 caracteres y sin duplicados.
    @NotBlank(message = "El nombre de la unidad de medida es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    // Abreviatura obligatoria de la unidad de medida, con un máximo de 15 caracteres y sin duplicados.
    @NotBlank(message = "La abreviatura de la unidad de medida es obligatoria")
    @Size(max = 15, message = "La abreviatura no puede superar los 15 caracteres")
    @Column(nullable = false, unique = true, length = 15)
    private String abreviatura;

    // Indica si la unidad de medida está activa y puede utilizarse en nuevos materiales.
    @Column(nullable = false)
    private Boolean activo = true;

    // Constructor vacío necesario para que JPA pueda crear instancias de la entidad.
    public UnidadMedida() {
    }

    // Devuelve el identificador de la unidad de medida.
    public Long getId() {
        return id;
    }

    // Establece el identificador de la unidad de medida.
    public void setId(Long id) {
        this.id = id;
    }

    // Devuelve el nombre de la unidad de medida.
    public String getNombre() {
        return nombre;
    }

    // Establece el nombre de la unidad de medida.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Devuelve la abreviatura de la unidad de medida.
    public String getAbreviatura() {
        return abreviatura;
    }

    // Establece la abreviatura de la unidad de medida.
    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    // Devuelve el estado de actividad de la unidad de medida.
    public Boolean getActivo() {
        return activo;
    }

    // Establece el estado de actividad de la unidad de medida.
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
