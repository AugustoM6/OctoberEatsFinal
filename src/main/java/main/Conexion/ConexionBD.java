package main.Conexion;

import java.sql.*;

public class ConexionBD {
    private Connection conexion = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultado = null;

    private static final String URL = "jdbc:mysql://localhost:3306/OctoberEats";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    // Método para establecer la conexión con la base de datos
    public void setConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No fue posible conectarse a la base de datos", e);
        }
    }

    // Método para preparar la consulta SQL
    public void setConsulta(String sql) throws SQLException {
        if (conexion != null) {
            this.preparedStatement = conexion.prepareStatement(sql);
        }
    }

    // Método para obtener el PreparedStatement
    public PreparedStatement getPreparedStatement() {
        return this.preparedStatement;
    }

    // Método para cerrar la conexión y liberar recursos
    public void cerrarConexion() {
        try {
            if (resultado != null) {
                resultado.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método estático para obtener una nueva conexión
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