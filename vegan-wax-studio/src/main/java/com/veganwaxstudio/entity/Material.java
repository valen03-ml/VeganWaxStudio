
package com.veganwaxstudio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

// Entidad que representa los materiales e insumos utilizados en los tratamientos de depilación del centro.
@Entity
@Table(name = "material")
public class Material {

    // Identificador único del material, generado automáticamente por la base de datos.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre obligatorio del material, con un máximo de 100 caracteres.
    @NotBlank(message = "El nombre del material es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    // Descripción obligatoria del material, con un máximo de 255 caracteres.
    @NotBlank(message = "La descripción del material es obligatoria")
    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    @Column(nullable = false, length = 255)
    private String descripcion;

    // Marca del material, con un máximo de 100 caracteres.
    @NotBlank(message = "La marca del material es obligatoria")
    @Size(max = 100, message = "La marca no puede superar los 100 caracteres")
    @Column(nullable = false, length = 100)
    private String marca;

    // Categoría a la que pertenece el material.
    @NotNull(message = "La categoría del material es obligatoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaInsumo categoria;

    // Proveedor que suministra el material.
    @NotNull(message = "El proveedor del material es obligatorio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    // Unidad de medida utilizada para controlar las existencias del material.
    @NotNull(message = "La unidad de medida es obligatoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unidad_medida_id", nullable = false)
    private UnidadMedida unidadMedida;

    // Fecha de caducidad del material, opcional cuando no corresponda.
    @Column(name = "fecha_caducidad")
    private LocalDate fechaCaducidad;

    // Cantidad disponible del material, que no puede ser negativa.
    @NotNull(message = "La cantidad disponible es obligatoria")
    @Min(value = 0, message = "Las existencias no pueden ser negativas")
    @Column(name = "cantidad_disponible", nullable = false)
    private Integer cantidadDisponible = 0;

    // Indica si el material está activo y disponible para nuevas operaciones de inventario.
    @Column(nullable = false)
    private Boolean activo = true;

    // Constructor vacío necesario para que JPA pueda crear instancias de la entidad.
    public Material() {
    }

    // Devuelve el identificador del material.
    public Long getId() {
        return id;
    }

    // Establece el identificador del material.
    public void setId(Long id) {
        this.id = id;
    }

    // Devuelve el nombre del material.
    public String getNombre() {
        return nombre;
    }

    // Establece el nombre del material.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Devuelve la descripción del material.
    public String getDescripcion() {
        return descripcion;
    }

    // Establece la descripción del material.
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Devuelve la marca del material.
    public String getMarca() {
        return marca;
    }

    // Establece la marca del material.
    public void setMarca(String marca) {
        this.marca = marca;
    }

    // Devuelve la categoría del material.
    public CategoriaInsumo getCategoria() {
        return categoria;
    }

    // Establece la categoría del material.
    public void setCategoria(CategoriaInsumo categoria) {
        this.categoria = categoria;
    }

    // Devuelve el proveedor del material.
    public Proveedor getProveedor() {
        return proveedor;
    }

    // Establece el proveedor del material.
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    // Devuelve la unidad de medida del material.
    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    // Establece la unidad de medida del material.
    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    // Devuelve la fecha de caducidad del material.
    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    // Establece la fecha de caducidad del material.
    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    // Devuelve la cantidad disponible del material.
    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    // Establece la cantidad disponible del material.
    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    // Devuelve el estado de actividad del material.
    public Boolean getActivo() {
        return activo;
    }

    // Establece el estado de actividad del material.
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
