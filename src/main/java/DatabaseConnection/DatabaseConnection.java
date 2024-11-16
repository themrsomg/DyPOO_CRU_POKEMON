package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
    public class DatabaseConnection {
        private static final String URL = "jdbc:mysql://localhost:3306/CRU_Pokemon_db";
        private static final String USER = "root";
        private static final String PASSWORD = "ElFerxxoEnRotoplas117";

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }
    */

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/CRU_Pokemon_db";
    private static final String USER = "root";
    private static final String PASSWORD = "ElFerxxoEnRotoplas117";

    private DatabaseConnection()throws SQLException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static DatabaseConnection getInstance() throws SQLException{
        if(instance == null || instance.getConnection().isClosed()){
            instance = new DatabaseConnection();
        }
        return instance;
    }
    public Connection getConnection(){
        return connection;
    }
}

