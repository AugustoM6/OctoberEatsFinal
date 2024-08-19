package main.Modulos;

import main.Conexion.ConexionBD;
import main.Interface.Negocio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Restaurante implements Negocio {
    private final int idRestaurante;
    private final String nombre;
    private final String direccion;
    private final String correo;
    private final String categoria;

    public Restaurante(int idRestaurante, String nombre, String direccion, String correo, String categoria) {
        this.idRestaurante = idRestaurante;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.categoria = categoria;
    }

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

    public static Restaurante cargarDesdeBaseDatos(int idRestaurante) throws SQLException {
        String query = "SELECT * FROM restaurantes WHERE restauranteID = ?";
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            conexion.setConsulta(query);
            conexion.getConsulta().setInt(1, idRestaurante);
            ResultSet resultSet = conexion.getResultado();

            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String direccion = resultSet.getString("direccion");
                String correo = resultSet.getString("email");
                String categoria = resultSet.getString("categoria");
                return new Restaurante(idRestaurante, nombre, direccion, correo, categoria);
            }
        } finally {
            conexion.cerrarConexion();
        }
        return null;
    }

    public static List<Restaurante> cargarTodosDesdeBaseDatos() throws SQLException {
        List<Restaurante> restaurantes = new ArrayList<>();
        String query = "SELECT * FROM restaurantes";
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            conexion.setConsulta(query);
            ResultSet resultSet = conexion.getResultado();

            while (resultSet.next()) {
                int idRestaurante = resultSet.getInt("restauranteID");
                String nombre = resultSet.getString("nombre");
                String direccion = resultSet.getString("direccion");
                String correo = resultSet.getString("email");
                String categoria = resultSet.getString("categoria");
                restaurantes.add(new Restaurante(idRestaurante, nombre, direccion, correo, categoria));
            }
        } finally {
            conexion.cerrarConexion();
        }
        return restaurantes;
    }

    public void guardarEnBaseDatos() throws SQLException {
        String query = "INSERT INTO restaurantes (nombre, direccion, email, categoria) VALUES (?, ?, ?, ?)";
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            conexion.setConsulta(query);
            conexion.getConsulta().setString(1, getNombre());
            conexion.getConsulta().setString(2, getDireccion());
            conexion.getConsulta().setString(3, getEmail());
            conexion.getConsulta().setString(4, getCategoria());
            conexion.getConsulta().executeUpdate();
        } finally {
            conexion.cerrarConexion();
        }
    }
}