package org.ignacio.rios.service;

import org.ignacio.rios.Repositorio.CategoriaRepositorioImpl;
import org.ignacio.rios.Repositorio.ProductoRepositorioImpl;
import org.ignacio.rios.Repositorio.Repositorio;
import org.ignacio.rios.models.Categoria;
import org.ignacio.rios.models.Producto;
import org.ignacio.rios.util.ConeccionBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CatalogSerivicio implements Servicio{

    private Repositorio<Producto> repositorioProducto ; // atributos de clase
    private Repositorio<Categoria> repositorioCategoria; // atributos de calse

    public CatalogSerivicio() {
        this.repositorioProducto = new ProductoRepositorioImpl();
        this.repositorioCategoria = new CategoriaRepositorioImpl();
    }




    @Override
    public List<Producto> listar() throws SQLException {
        try(Connection conn = ConeccionBD.getConnection()){
            repositorioProducto.setConn(conn);

            return repositorioProducto.lista();

        }

    }

    @Override
    public Producto porId(Long id) throws SQLException {
        try(Connection conn = ConeccionBD.getConnection()){
            repositorioProducto.setConn(conn);

            return repositorioProducto.porId(id);
        }

    }

    @Override
    public Producto guardar(Producto producto) throws SQLException {
        try(Connection conn = ConeccionBD.getConnection()){
            repositorioProducto.setConn(conn);
            if(conn.getAutoCommit()){
                conn.setAutoCommit(false);
            }
             Producto otroProducto = null;

              try{
                  otroProducto = repositorioProducto.guardar(producto);
                  conn.commit();

              }catch (SQLException e){
                  conn.rollback();
                  e.printStackTrace();


            }

            return otroProducto;
        }

    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try(Connection conn = ConeccionBD.getConnection()){
            repositorioProducto.setConn(conn);
            if(conn.getAutoCommit()){
                conn.setAutoCommit(false);
            }
               try{
                   repositorioProducto.eliminar(id);
                   conn.commit();
               }catch(SQLException e ){
                   conn.rollback();
                   e.printStackTrace();
               }

        }

    }

    @Override
    public List<Categoria> listarCategoria() throws SQLException {

        try(Connection conn = ConeccionBD.getConnection()){
            repositorioCategoria.setConn(conn);
                 return repositorioCategoria.lista();
        }

    }

    @Override
    public Categoria porIdCategoria(Long id) throws SQLException {
        try(Connection conn = ConeccionBD.getConnection()){
            repositorioCategoria.setConn(conn);
            return repositorioCategoria.porId(id);

        }

    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) throws SQLException {
        try(Connection conn = ConeccionBD.getConnection()){
            repositorioCategoria.setConn(conn);
            if(conn.getAutoCommit()){
                conn.setAutoCommit(false);
            }
            Categoria otraNvaCategoria = null;
            try {
                otraNvaCategoria = repositorioCategoria.guardar(categoria);
                conn.commit();

            }catch (SQLException e){
                conn.rollback();
                e.printStackTrace();
            }
            return otraNvaCategoria;
        }

    }

    @Override
    public void eliminarCategoria(Long id) throws SQLException {
        try(Connection conn = ConeccionBD.getConnection()){
            repositorioCategoria.setConn(conn);
            if(conn.getAutoCommit()){
                conn.setAutoCommit(false);
              }
            try{
                repositorioCategoria.eliminar(id);
                conn.commit();
               }catch(SQLException e){
                    conn.rollback();
                    e.printStackTrace();
            }
        }
    }

    @Override
    public void guardarProductoConCategoria(Producto producto, Categoria categoria) throws SQLException {
        try(Connection conn = ConeccionBD.getConnection()){
            repositorioProducto.setConn(conn);
            repositorioCategoria.setConn(conn);
            if(conn.getAutoCommit()){
                conn.setAutoCommit(false);
            }
            try{
                Categoria NvaCategori = repositorioCategoria.guardar(categoria);
                producto.setCategoria(NvaCategori);
                repositorioProducto.guardar(producto);
                conn.commit();
            }catch(SQLException e){
                conn.rollback();
                e.printStackTrace();
            }


        }



    }
}
