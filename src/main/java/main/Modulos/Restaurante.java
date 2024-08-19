package main.Modulos;

import main.Conexion.ConexionBD;
import main.Interface.Negocio;

import java.sql.SQLException;

public class Restaurante implements Negocio {
    private final int idRestaurante;
    private final String nombre;
    private final String direccion;
    private final String correo;
    private final String categoria;

    // Constructor para inicializar un nuevo restaurante
    public Restaurante(int idRestaurante, String nombre, String direccion, String correo, String categoria) {
        this.idRestaurante = idRestaurante;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.categoria = categoria;
    }

    // Getters para los atributos del restaurante
    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public String getDireccion() {
        return direccion;
    }

    @Override
    public String getEmail() {
        return correo;
    }

    @Override
    public String getCategoria() {
        return categoria;
    }

    public int getIdRestaurante() {
        return idRestaurante;
    }

    // Método para guardar el restaurante en la base de datos
    public void guardarEnBaseDatos() throws SQLException {
        String query = "INSERT INTO restaurantes (nombre, direccion, email, categoria) VALUES (?, ?, ?, ?)";
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            conexion.setConsulta(query);
            conexion.getPreparedStatement().setString(1, getNombre());
            conexion.getPreparedStatement().setString(2, getDireccion());
            conexion.getPreparedStatement().setString(3, getEmail());
            conexion.getPreparedStatement().setString(4, getCategoria());
            conexion.getPreparedStatement().executeUpdate();
        } finally {
            conexion.cerrarConexion();
        }
    }
}