package main.Modulos;

import main.Conexion.ConexionBD;
import main.Interface.Negocio;

import java.sql.SQLException;

public class Pedido {
    private int idPedido;
    private final Usuario cliente;
    private final Negocio restaurante;
    private String estado;
    private final double precio;

    // Constructor para inicializar un nuevo pedido
    public Pedido(int idPedido, Usuario cliente, Negocio restaurante, double precio) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.restaurante = restaurante;
        this.estado = "pendiente"; // Estado inicial del pedido
        this.precio = precio;
    }

    // Getters y setters para los atributos del pedido
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

    // Método para guardar el pedido en la base de datos
    public void guardarEnBaseDatos() throws SQLException {
        String query = "INSERT INTO pedidos (userID, restauranteID, estado, precio) VALUES (?, ?, ?, ?)";
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            conexion.setConsulta(query);
            conexion.getPreparedStatement().setString(1, getCliente().getUserID());
            conexion.getPreparedStatement().setInt(2, ((Restaurante) getRestaurante()).getIdRestaurante());
            conexion.getPreparedStatement().setString(3, getEstado());
            conexion.getPreparedStatement().setDouble(4, getPrecio());
            conexion.getPreparedStatement().executeUpdate();
        } finally {
            conexion.cerrarConexion();
        }
    }
}