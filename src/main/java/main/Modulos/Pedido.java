package main.Modulos;

import main.Conexion.ConexionBD;
import main.Interface.Negocio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Pedido {
    private int idPedido;
    private final Usuario cliente;
    private final Negocio restaurante;
    private String estado;
    private final double precio;

    public Pedido(int idPedido, Usuario cliente, Negocio restaurante, double precio) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.restaurante = restaurante;
        this.estado = "pendiente";
        this.precio = precio;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public Negocio getRestaurante() {
        return restaurante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getPrecio() {
        return precio;
    }

    public static Pedido cargarDesdeBaseDatos(int idPedido) throws SQLException {
        String query = "SELECT * FROM pedidos WHERE pedidoID = ?";
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            conexion.setConsulta(query);
            conexion.getConsulta().setInt(1, idPedido);
            ResultSet resultSet = conexion.getResultado();

            if (resultSet.next()) {
                String userID = resultSet.getString("userID");
                int restauranteID = resultSet.getInt("restauranteID");
                String estado = resultSet.getString("estado");
                double precio = resultSet.getDouble("precio");

                Usuario cliente = Usuario.loadFromDatabase(userID);
                Restaurante restaurante = Restaurante.cargarDesdeBaseDatos(restauranteID);

                Pedido pedido = new Pedido(idPedido, cliente, restaurante, precio);
                pedido.setEstado(estado);
                return pedido;
            }
        } finally {
            conexion.cerrarConexion();
        }
        return null;
    }

    public void guardarEnBaseDatos() throws SQLException {
        String query = "INSERT INTO pedidos (userID, restauranteID, estado, precio) VALUES (?, ?, ?, ?)";
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            conexion.setConsulta(query);
            conexion.getConsulta().setString(1, getCliente().getUserID());
            conexion.getConsulta().setInt(2, ((Restaurante) getRestaurante()).getIdRestaurante());
            conexion.getConsulta().setString(3, getEstado());
            conexion.getConsulta().setDouble(4, getPrecio());
            conexion.getConsulta().executeUpdate();
        } finally {
            conexion.cerrarConexion();
        }
    }
}