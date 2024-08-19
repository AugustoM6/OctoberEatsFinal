package main.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexionBD {
    private Connection conexion = null;
    private PreparedStatement consulta = null;
    private ResultSet resultado = null;

    private static final String URL = "jdbc:mysql://localhost:3306/OctoberEats";
    private static final String USER = "root";
    private static final String PASSWORD = "CesarM60";

    public void setConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No fue posible conectarse a la base de datos", e);
        }
    }

    public void setConsulta(String sql) throws SQLException {
        if (conexion != null) {
            this.consulta = conexion.prepareStatement(sql);
        }
    }

    public PreparedStatement getConsulta() {
        return this.consulta;
    }

    public ResultSet getResultado() throws SQLException {
        if (consulta != null) {
            return consulta.executeQuery();
        }
        return null;
    }

    public void cerrarConexion() {
        try {
            if (resultado != null) {
                resultado.close();
            }
            if (consulta != null) {
                consulta.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No fue posible conectarse a la base de datos", e);
        }
        return connection;
    }
}