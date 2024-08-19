package main.Modulos;

import main.Conexion.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario extends PersonaImpl {
    private Rol rol;

    public enum Rol {
        CLIENTE,
        REPARTIDOR
    }

    public Usuario(String userID, String direccion, String contrasena, String nombre, String correo, Rol rol) {
        super(userID, direccion, contrasena, nombre, correo);
        this.rol = rol;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public static Usuario loadFromDatabase(String userID) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE userID = ?";
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            conexion.setConsulta(query);
            conexion.getConsulta().setString(1, userID);
            ResultSet resultSet = conexion.getResultado();

            if (resultSet.next()) {
                String direccion = resultSet.getString("direccion");
                String contrasenaHush = resultSet.getString("contrasenaHash");
                String nombre = resultSet.getString("nombre");
                String correo = resultSet.getString("correo");
                Rol rol = Rol.valueOf(resultSet.getString("rol"));
                return new Usuario(userID, direccion, contrasenaHush, nombre, correo, rol);
            }
        } finally {
            conexion.cerrarConexion();
        }
        return null;
    }

    public static Usuario loadByCorreo(String correo) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE correo = ?";
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            conexion.setConsulta(query);
            conexion.getConsulta().setString(1, correo);
            ResultSet resultSet = conexion.getResultado();

            if (resultSet.next()) {
                String userID = resultSet.getString("userID");
                String direccion = resultSet.getString("direccion");
                String contrasenaHush = resultSet.getString("contrasenaHash");
                String nombre = resultSet.getString("nombre");
                Rol rol = Rol.valueOf(resultSet.getString("rol"));
                return new Usuario(userID, direccion, contrasenaHush, nombre, correo, rol);
            }
        } finally {
            conexion.cerrarConexion();
        }
        return null;
    }

    public boolean verifyPassword(String contrasena) {
        return this.getContrasena().equals(Seguridad.hashPassword(contrasena));
    }

    public void saveToDatabase() throws SQLException {
        String query = "INSERT INTO usuarios (userID, nombre, direccion, correo, contrasenaHash, rol) VALUES (?, ?, ?, ?, ?, ?)";
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            conexion.setConsulta(query);
            conexion.getConsulta().setString(1, getUserID());
            conexion.getConsulta().setString(2, getNombre());
            conexion.getConsulta().setString(3, getDireccion());
            conexion.getConsulta().setString(4, getCorreo());
            conexion.getConsulta().setString(5, getContrasena());
            conexion.getConsulta().setString(6, getRol().name());
            conexion.getConsulta().executeUpdate();
        } finally {
            conexion.cerrarConexion();
        }
    }
}