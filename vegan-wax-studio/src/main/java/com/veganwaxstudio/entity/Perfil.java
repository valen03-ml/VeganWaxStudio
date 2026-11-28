
package com.veganwaxstudio.entity;

//Se importa las anotaciones de JPA para relacionar clase Java con una tabla en MySQL
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

//Entidad que representa los perfiles de usuario del sistema (Tabla "perfil" en MySQL)
@Entity
@Table(name = "perfil")
public class Perfil {

    // Atributos de la entidad "perfil"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @Column(nullable = false)
    private Boolean activo = true;

    public Perfil() {
    }

    // Métodos de acceso y modificación (Getters y setters)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    // Modifica el estado del perfil
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
