package main.UI;

import main.Modulos.GestorRestaurantes;
import main.Modulos.Restaurante;
import main.Modulos.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuUI extends JFrame {
    private JTextField barraBusqueda;
    private JButton searchButton;
    private JButton btnInicio;
    private JButton btnCarrito;
    private JButton btnPedidos;
    private JButton btnCuenta;
    private JButton btnRetroceder;
    private JPanel resultsPanel;
    private Usuario usuario;
    private GestorRestaurantes gestorRestaurantes;

    public MenuUI(Usuario usuario) {
        this.usuario = usuario;
        this.gestorRestaurantes = new GestorRestaurantes();

        setTitle("October Eats - Menú Principal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel();
        barraBusqueda = new JTextField(20);
        searchButton = new JButton("Buscar");
        searchPanel.add(barraBusqueda);
        searchPanel.add(searchButton);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
        btnInicio = new JButton("Inicio");
        btnCarrito = new JButton("Carrito");
        btnPedidos = new JButton("Pedidos");
        btnCuenta = new JButton("Cuenta");
        btnRetroceder = new JButton("Retroceder");
        buttonPanel.add(btnInicio);
        buttonPanel.add(btnCarrito);
        buttonPanel.add(btnPedidos);
        buttonPanel.add(btnCuenta);
        buttonPanel.add(btnRetroceder);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultsPanel);

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarRestaurantes();
            }
        });

        btnInicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarInicio();
            }
        });

        btnCarrito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCarrito();
            }
        });

        btnPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPedidos();
            }
        });

        btnCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCuenta();
            }
        });

        btnRetroceder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retrocederAlMenuPrincipal();
            }
        });

        // Muestra todos los restaurantes al inicio
        mostrarTodosLosRestaurantes();
    }

    private void buscarRestaurantes() {
        String busqueda = barraBusqueda.getText().toLowerCase();
        resultsPanel.removeAll();

        if (!busqueda.trim().isEmpty()) {
            List<Restaurante> resultados = gestorRestaurantes.buscarRestaurantes(busqueda);
            if (resultados.isEmpty()) {
                resultsPanel.add(new JLabel("No se encontraron restaurantes"));
            } else {
                mostrarRestaurantes(resultados);
            }
        } else {
            resultsPanel.add(new JLabel("Input inválido"));
        }
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void mostrarInicio() {
        JOptionPane.showMessageDialog(null, "Bienvenido al menú principal");
    }

    private void mostrarCarrito() {
        resultsPanel.removeAll();
        resultsPanel.add(new JLabel("El carrito está vacío"));
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void mostrarPedidos() {
        resultsPanel.removeAll();
        resultsPanel.add(new JLabel("No tienes pedidos en este momento"));
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void mostrarCuenta() {
        JOptionPane.showMessageDialog(this, "Usuario: " + usuario.getNombre() + "\nCorreo: " + usuario.getCorreo() + "\nDirección: " + usuario.getDireccion());
    }

    private void mostrarTodosLosRestaurantes() {
        List<Restaurante> todosLosRestaurantes = gestorRestaurantes.getTodosLosRestaurantes();
        if (todosLosRestaurantes.isEmpty()) {
            resultsPanel.add(new JLabel("No hay restaurantes disponibles"));
        } else {
            mostrarRestaurantes(todosLosRestaurantes);
        }
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void mostrarRestaurantes(List<Restaurante> restaurantes) {
        resultsPanel.removeAll();
        for (Restaurante restaurante : restaurantes) {
            JPanel restaurantePanel = new JPanel(new GridLayout(0, 1));
            JTextArea restauranteInfo = new JTextArea();
            restauranteInfo.setText("Nombre: " + restaurante.getNombre() + "\n" +
                    "Direccion: " + restaurante.getDireccion() + "\n" +
                    "Email: " + restaurante.getEmail() + "\n" +
                    "Categoría: " + restaurante.getCategoria());
            restauranteInfo.setEditable(false);
            restaurantePanel.add(restauranteInfo);
            resultsPanel.add(restaurantePanel);
        }
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void retrocederAlMenuPrincipal() {
        MainMenuUI mainMenu = new MainMenuUI();
        mainMenu.setVisible(true);
        dispose();
    }
}