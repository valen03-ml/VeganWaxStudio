
package com.veganwaxstudio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa los datos generales del centro.
 * Se corresponde con la tabla "centro" de la base de datos.
 */
@Entity
@Table(name = "centro")
public class Centro {

    // Atributos de la entidad
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del centro es obligatorio.")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria.")
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String direccion;

    @NotBlank(message = "El teléfono es obligatorio.")
    @Pattern(
        regexp = "^\\+?[0-9][0-9\\s()-]{6,19}$",
        message = "El formato del teléfono no es válido."
    )
    @Column(nullable = false, length = 20)
    private String telefono;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El formato del correo electrónico no es válido.")
    @Size(max = 100)
    @Column(name = "correo_electronico", nullable = false, length = 100)
    private String correoElectronico;

    @NotBlank(message = "Los datos fiscales son obligatorios.")
    @Size(max = 255)
    @Column(name = "datos_fiscales", nullable = false, length = 255)
    private String datosFiscales;

    // Constructor
    public Centro() {
    }

    // Métodos de acceso y modificación (getters y setters)
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDatosFiscales() {
        return datosFiscales;
    }

    public void setDatosFiscales(String datosFiscales) {
        this.datosFiscales = datosFiscales;
    }
}
