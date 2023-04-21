package org.ignacio.rios;

import org.ignacio.rios.models.Categoria;
import org.ignacio.rios.util.ConeccionBD;

import javax.xml.transform.sax.SAXResult;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EjemploJDBC {

    public static void main(String[] args) {

        try (Connection conn = ConeccionBD.getConnection(); // esta hace un autoclose try con recursos
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM productos");
             ResultSet rs = stmt.executeQuery()) {

            List<String> resultados = new ArrayList<>();
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String id = rs.getString("id");
                int precio= rs.getInt("precio");
                resultados.add(nombre + "  |" + id+"|"+ precio);
            }

            System.out.format("%-20s | %-10s | %7s%n", "Nombre", "ID", "Precio");
            System.out.format("%s%n", "-----------------------------------------");

            for (String resultado : resultados) {
                String[] parts = resultado.split("\\|");
                String nombre = parts[0].trim();
                String id = parts[1].trim();
                String precio = parts[2].trim();
                System.out.format("%-20s | %-10s | %7s%n", nombre, id, precio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
