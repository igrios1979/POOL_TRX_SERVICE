package org.ignacio.rios;

import org.ignacio.rios.Repositorio.CategoriaRepositorioImpl;
import org.ignacio.rios.Repositorio.ProductoRepositorioImpl;
import org.ignacio.rios.Repositorio.Repositorio;
import org.ignacio.rios.models.Categoria;
import org.ignacio.rios.models.Producto;
import org.ignacio.rios.util.ConeccionBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
/*UPDATE*/

public class EjemploJDBC2 {

    public static void main(String[] args) throws SQLException {



            try (Connection conn = ConeccionBD.getConnection()) {

                    if(conn.getAutoCommit()){
                            conn.setAutoCommit(false);
                    }
                     try {
                             Repositorio<Producto> repositorio = new ProductoRepositorioImpl(conn);
                             Repositorio<Categoria> repocategoria = new CategoriaRepositorioImpl(conn);
                             System.out.println("=========================|Insertar Categoria|=========================");
                             Categoria cat = new Categoria();
                             cat.setNombre("Art. fiambreria");
                             Categoria CatconUltimoId = repocategoria.guardar(cat); // devuelve categoia con ultimo ID
                             System.out.println("categoria guardada con Exito..."+CatconUltimoId.getId() +" "+CatconUltimoId.getNombre());





                             System.out.println("=========================|LISTAR||=========================");
                             repositorio.lista().forEach(System.out::println);
                             System.out.println("=========================|PORID||=========================");
                             System.out.println(repositorio.porId(14L));
                             System.out.println("=========================|INSERTANDO|=========================");
                             Producto p = new Producto();
                             // p.setId(1L);
                             p.setNombre("Salame milan");
                             p.setPrecio(56002);
                             p.setFechaRegistro(new Date());
                             p.setCategoria(CatconUltimoId);
                             p.setSku("FIA-002");
                             repositorio.guardar(p);
                             System.out.println("Guardado OK " + p.getId());
                             repositorio.lista().forEach(System.out::println);

                             conn.commit();

                     } catch (SQLException e) {
                             conn.rollback();
                            e.printStackTrace();
                     }


            }//fin try

    }

}

