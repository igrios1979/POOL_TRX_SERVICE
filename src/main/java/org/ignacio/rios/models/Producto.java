package org.ignacio.rios.models;

import java.util.Date;


import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor

//@ToString

public class Producto {

    private Long id;
    private Integer precio;
    private Date fechaRegistro;
    private String nombre;
    private Categoria categoria;
    private String sku;

   @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", precio=" + precio +
                ", fechaRegistro=" + fechaRegistro +
                ", nombre='" + nombre + '\'' +
                ",categoria = " + categoria.getNombre() +
                ",  sku=  "+ sku +
                '}';
    }


}
