package ua.andrii.verkholiak.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

final class DataBaseConnector {

    private DataBaseConnector() {
    }

    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost/university" +
            "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";

    private static Connection connection;

    static Connection getConnection() {
        try {
            if (connection == null) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
