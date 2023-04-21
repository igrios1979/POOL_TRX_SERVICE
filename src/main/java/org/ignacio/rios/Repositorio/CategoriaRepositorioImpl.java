package org.ignacio.rios.Repositorio;

import org.ignacio.rios.models.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepositorioImpl implements Repositorio<Categoria>{

    private Connection conn;

    public CategoriaRepositorioImpl() {
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public CategoriaRepositorioImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Categoria> lista() throws SQLException {
        List<Categoria> listaCategorias= new ArrayList<>();
         String sql = "SELECT * FROM  categorias";


        try(Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT * FROM  categorias");
            while(rs.next()){
                Categoria categoria = getCategoria(rs);
                /*-----------------------------*/
                listaCategorias.add(categoria);
            }
        }
        return listaCategorias;
    }



    @Override
    public Categoria porId(Long id) throws SQLException {
        Categoria cat =  null;
        String sql = "SELECT * FROM categorias WHERE id = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setLong(1,id);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    cat = getCategoria(rs);
                }
            }
        }
        return cat;
    }

    @Override
    public Categoria guardar(Categoria categoria) throws SQLException {
         String sql = null;
     if(categoria.getId() != null && categoria.getId() > 0) {
         sql = "UPDATE categorias SET nombre = ? WHERE id=?";
     }else{
         sql = "INSERT INTO categorias(nombre)VALUES(?)";;
         }

         try(PreparedStatement pstm = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
             pstm.setString(1,categoria.getNombre());
             if(categoria.getId() != null && categoria.getId() > 0){
                 pstm.setLong(2,categoria.getId());
             }
                pstm.executeUpdate();

             if(categoria.getId() == null){
                 try(ResultSet rs = pstm.getGeneratedKeys()){
                     if(rs.next()){
                         categoria.setId(rs.getLong(1));
                     }
                 }
             }
         }

     return categoria;
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try(PreparedStatement stmt =conn.prepareStatement("DELETE FROM categorias where id=?"); ){
            stmt.setLong(1,id);
            stmt.executeUpdate();
        }
    }

    private static Categoria getCategoria(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("id"));
        categoria.setNombre(rs.getString("nombre"));
        return categoria;
    }



}
