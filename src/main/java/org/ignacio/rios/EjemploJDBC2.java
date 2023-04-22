package org.ignacio.rios;

import org.ignacio.rios.Repositorio.CategoriaRepositorioImpl;
import org.ignacio.rios.Repositorio.ProductoRepositorioImpl;
import org.ignacio.rios.Repositorio.Repositorio;
import org.ignacio.rios.models.Categoria;
import org.ignacio.rios.models.Producto;
import org.ignacio.rios.service.CatalogSerivicio;
import org.ignacio.rios.service.Servicio;
import org.ignacio.rios.util.ConeccionBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
/*UPDATE*/

public class EjemploJDBC2 {

    public static void main(String[] args) throws SQLException {

        Servicio service = new CatalogSerivicio();
        System.out.println("=========================|Insertar Categoria|=========================");
        Categoria cat = new Categoria();
        cat.setNombre("Iluminacion");

        //   System.out.println("categoria guardada con Exito..." + CatconUltimoId.getId() + " " + CatconUltimoId.getNombre());


        System.out.println("=========================|LISTAR||=========================");
        service.listar().forEach(System.out::println);

        System.out.println("=========================|INSERTANDO|=========================");
        Producto p = new Producto();
        // p.setId(1L);
        p.setNombre("LAMPARA LED Verde ");
        p.setPrecio(9563);
        p.setFechaRegistro(new Date());
        p.setSku("ILU-002");
        service.guardarProductoConCategoria(p,cat);
        System.out.println("Guardado OK " + p.getId());
        service.listar().forEach(System.out::println);


    }

}