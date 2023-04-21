package org.ignacio.rios.Repositorio;

import org.ignacio.rios.models.Categoria;
import org.ignacio.rios.models.Producto;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioImpl implements Repositorio<Producto>{


    private Connection conn;

    public ProductoRepositorioImpl(Connection conn) {
        this.conn = conn;
    }

    public ProductoRepositorioImpl() {
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Producto> lista() throws SQLException {

    List<Producto> productos = new ArrayList<>();

    try(Statement stmt = conn.createStatement()){

        ResultSet rs = stmt.executeQuery("SELECT p.*,c.nombre as cat FROM Productos p INNER JOIN categorias c ON p.categoria_id = c.id");
        while(rs.next()){
            Producto p = CrearProducto(rs);
            productos.add(p);
        }
    }
        return productos;
    }


    @Override
    public Producto porId(Long id) throws SQLException {
        Producto producto = null;

        try(PreparedStatement stmt = conn.prepareStatement("SELECT p.*,c.nombre as cat FROM Productos p INNER JOIN categorias c ON p.categoria_id = c.id WHERE p.id = ?"))
        {
            stmt.setLong(1,id);
            try (ResultSet res = stmt.executeQuery()) {
                if (res.next()) {
                    producto = CrearProducto(res);

                }


            }
        }

        return producto;
    }

    @Override
    public Producto guardar(Producto producto) throws SQLException {
        String sql;
        if (producto.getId()!= null && producto.getId()>0) {
            sql = "UPDATE productos SET nombre = ?, precio=?,categoria_id=? sku=? where id=? ";
        } else {
            sql = "INSERT INTO productos(nombre,precio,categoria_id,sku,fecha)VALUES(?,?,?,?,?)";
        }

        try(  PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
              stmt.setString(1,producto.getNombre());
              stmt.setLong(2,producto.getPrecio());
              if(producto.getCategoria()!=null) {
                  stmt.setLong(3, producto.getCategoria().getId());
              }else{
                  stmt.setNull(3, java.sql.Types.INTEGER);
              }
              stmt.setString(4,producto.getSku());
            if (producto.getId()!=null && producto.getId()>0) {
                stmt.setLong(5, producto.getId());
            }else{
                stmt.setDate(5,new Date(producto.getFechaRegistro().getTime()));
            }
            stmt.executeUpdate();
           // return producto;
            if(producto.getId() == null){
                try(ResultSet rs = stmt.getGeneratedKeys()){
                    if(rs.next()){
                        producto.setId(rs.getLong(1));
                    }
                }
            }
           return producto;
    }
}

    @Override
    public void eliminar(Long id) throws SQLException {

    String sql = "DELETE  FROM productos where id = ?";
    try(PreparedStatement stmtdel = conn.prepareStatement(sql)){
                              stmtdel.setLong(1,id);
                              stmtdel.executeUpdate();

                            }

         }

    private static Producto CrearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getInt("precio"));
        p.setFechaRegistro(rs.getDate("fecha"));
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("categoria_id"));
        categoria.setNombre(rs.getString("cat"));
        p.setSku(rs.getString("sku"));
         p.setCategoria(categoria);
        return p;
    }
}
