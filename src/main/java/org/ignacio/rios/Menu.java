import org.ignacio.rios.models.Categoria;
import org.ignacio.rios.models.Producto;
import org.ignacio.rios.service.CatalogSerivicio;
import org.ignacio.rios.service.Servicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;

public class Menu{

    private static Servicio service;

    public static void main(String[] args) throws SQLException {
        service = new CatalogSerivicio();

        JFrame frame = new JFrame("Menú de productos y categorías");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel agregarPanel = new JPanel();
        agregarPanel.setLayout(new GridLayout(0, 2));

        agregarPanel.add(new JLabel("Nombre de la categoría:"));
        JTextField categoriaTextField = new JTextField();
        agregarPanel.add(categoriaTextField);

        agregarPanel.add(new JLabel("Nombre del producto:"));
        JTextField nombreProductoTextField = new JTextField();
        agregarPanel.add(nombreProductoTextField);

        agregarPanel.add(new JLabel("Precio:"));
        JTextField precioTextField = new JTextField();
        agregarPanel.add(precioTextField);

        agregarPanel.add(new JLabel("SKU:"));
        JTextField skuTextField = new JTextField();
        agregarPanel.add(skuTextField);

        agregarPanel.add(new JLabel("Categoría:"));
        JComboBox<Categoria> categoriaComboBox = new JComboBox<>();
        service.listarCategoria().forEach(categoriaComboBox::addItem);
        agregarPanel.add(categoriaComboBox);

        JButton agregarCategoriaButton = new JButton("Agregar categoría");
        agregarCategoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Categoria cat = new Categoria();
                cat.setNombre(categoriaTextField.getText());
                try {
                    service.guardarCategoria(cat);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(frame, "Categoría agregada exitosamente.");
                categoriaComboBox.addItem(cat);
                categoriaTextField.setText("");
            }
        });
        agregarPanel.add(agregarCategoriaButton);

        JButton agregarProductoButton = new JButton("Agregar producto");
        agregarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Producto p = new Producto();
                p.setNombre(nombreProductoTextField.getText());
                p.setPrecio((int) Double.parseDouble(precioTextField.getText()));
                p.setFechaRegistro(new Date());
                p.setSku(skuTextField.getText());
                p.setCategoria((Categoria) categoriaComboBox.getSelectedItem());
                try {
                    service.guardar(p);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(frame, "Producto agregado exitosamente.");
                nombreProductoTextField.setText("");
                precioTextField.setText("");
                skuTextField.setText("");
            }
        });
        agregarPanel.add(agregarProductoButton);

        frame.add(agregarPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
