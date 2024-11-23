package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    // Driver JDBC para SQL Server
    private static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    // URL de conexión para SQL Server
    private static final String DB_BASE_URL = "jdbc:sqlserver://localhost:1433;DatabaseName=TurneroMedico";

    // Credenciales para la base de datos SQL Server
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "121186";

    public static Connection connect() {
        Connection c = null;
        try {
            // Cargar el driver para SQL Server
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }

        try {
            // Establecer la conexión a la base de datos SQL Server con autenticación SQL
            c = DriverManager.getConnection(DB_BASE_URL, DB_USERNAME, DB_PASSWORD);
            c.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return c;
    }

    public static String obtenerUbicacionBase() {
        // Retorna la URL de la base de datos SQL Server
        return DB_BASE_URL;
    }

}
