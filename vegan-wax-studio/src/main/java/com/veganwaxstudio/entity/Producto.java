
package com.veganwaxstudio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

// Entidad que representa los productos destinados a la venta presencial.
@Entity
@Table(name = "producto")
public class Producto {

    // Identificador único del producto, generado automáticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Código de referencia obligatorio y único para identificar el producto.
    @NotBlank(message = "El código de referencia es obligatorio")
    @Size(max = 50)
    @Column(name = "codigo_referencia", nullable = false, unique = true, length = 50)
    private String codigoReferencia;

    // Nombre del producto.
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    // Descripción de las características del producto.
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String descripcion;

    // Marca comercial del producto.
    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String marca;

    // Precio de venta del producto, almacenado con precisión decimal.
    @NotNull(message = "El precio de venta es obligatorio")
    @DecimalMin(value = "0.00", message = "El precio no puede ser negativo")
    @Column(name = "precio_venta", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioVenta;

    // Categoría a la que pertenece el producto.
    // Varios productos pueden pertenecer a una misma categoría.
    @NotNull(message = "La categoría es obligatoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaProducto categoria;

    // Cantidad de unidades disponibles actualmente en el inventario.
    @NotNull(message = "La cantidad disponible es obligatoria")
    @Min(value = 0, message = "Las existencias no pueden ser negativas")
    @Column(name = "cantidad_disponible", nullable = false)
    private Integer cantidadDisponible = 0;

    // Indica si el producto está activo y disponible para nuevas ventas.
    @Column(nullable = false)
    private Boolean activo = true;

    // Constructor vacío requerido por JPA para crear instancias de la entidad.
    public Producto() {
    }

    // Devuelve el identificador del producto.
    public Long getId() {
        return id;
    }

    // Establece el identificador del producto.
    public void setId(Long id) {
        this.id = id;
    }

    // Devuelve el código de referencia del producto.
    public String getCodigoReferencia() {
        return codigoReferencia;
    }

    // Establece el código de referencia del producto.
    public void setCodigoReferencia(String codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
    }

    // Devuelve el nombre del producto.
    public String getNombre() {
        return nombre;
    }

    // Establece el nombre del producto.
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Devuelve la descripción del producto.
    public String getDescripcion() {
        return descripcion;
    }

    // Establece la descripción del producto.
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Devuelve la marca del producto.
    public String getMarca() {
        return marca;
    }

    // Establece la marca del producto.
    public void setMarca(String marca) {
        this.marca = marca;
    }

    // Devuelve el precio de venta del producto.
    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    // Establece el precio de venta del producto.
    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    // Devuelve la categoría asociada al producto.
    public CategoriaProducto getCategoria() {
        return categoria;
    }

    // Establece la categoría a la que pertenece el producto.
    public void setCategoria(CategoriaProducto categoria) {
        this.categoria = categoria;
    }

    // Devuelve la cantidad disponible del producto.
    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    // Establece la cantidad disponible del producto.
    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    // Indica si el producto está activo.
    public Boolean getActivo() {
        return activo;
    }

    // Establece el estado de actividad del producto.
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
