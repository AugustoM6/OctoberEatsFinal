package main.Modulos;

import main.Conexion.ConexionBD;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario extends PersonaImpl {
    private Rol rol;

    public enum Rol {
        CLIENTE,
        REPARTIDOR
    }

    // Constructor para inicializar un nuevo usuario
    public Usuario(String userID, String direccion, String contrasena, String nombre, String correo, Rol rol) {
        super(userID, direccion, contrasena, nombre, correo);
        this.rol = rol;
    }

    // Getters y setters para el rol del usuario
    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    // Método para cargar un usuario desde la base de datos por correo
    public static Usuario loadByCorreo(String correo) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE correo = ?";
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            conexion.setConsulta(query);
            conexion.getPreparedStatement().setString(1, correo);
            ResultSet resultSet = conexion.getPreparedStatement().executeQuery();

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
        return null; // Si no se encuentra el usuario
    }

    // Método para verificar la contraseña del usuario
    public boolean verifyPassword(String contrasena) {
        return this.getContrasena().equals(Seguridad.hashPassword(contrasena));
    }

    // Método para guardar el usuario en la base de datos
    public void saveToDatabase() throws SQLException {
        String query = "INSERT INTO usuarios (userID, nombre, direccion, correo, contrasenaHash, rol) VALUES (?, ?, ?, ?, ?, ?)";
        ConexionBD conexion = new ConexionBD();
        conexion.setConexion();

        try {
            conexion.setConsulta(query);
            conexion.getPreparedStatement().setString(1, getUserID());
            conexion.getPreparedStatement().setString(2, getNombre());
            conexion.getPreparedStatement().setString(3, getDireccion());
            conexion.getPreparedStatement().setString(4, getCorreo());
            conexion.getPreparedStatement().setString(5, getContrasena());
            conexion.getPreparedStatement().setString(6, getRol().name());
            conexion.getPreparedStatement().executeUpdate();
        } finally {
            conexion.cerrarConexion();
        }
    }
}