package ua.andrii.verkholiak.repository;

import ua.andrii.verkholiak.entity.Degree;
import ua.andrii.verkholiak.entity.Lector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class LectorRepository {

    private static final String CONNECTION_ERROR_MESSAGE = "Connection error!";
    private static LectorRepository INSTANCE;
    private Connection connection;

    private LectorRepository() {
        connection = DataBaseConnector.getConnection();
    }

    public static LectorRepository getLectorRepository() {
        if (INSTANCE == null) {
            INSTANCE = new LectorRepository();
        }

        return INSTANCE;
    }

    public Set<Lector> findAllByDepartmentName(String departmentName) {
        Set<Lector> lectors = null;
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT lectors.* FROM   departments\n" +
                    "       INNER JOIN department_lector\n" +
                    "         ON department_lector.department_id = departments.id\n" +
                    "       INNER JOIN lectors\n" +
                    "         ON lectors.id = department_lector.lector_id\n" +
                    "WHERE  departments.name = " + "\'" + departmentName + "\'");

            lectors = getLectorsFromResultSet(resultSet);
            resultSet.close();
            statement.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(CONNECTION_ERROR_MESSAGE);
            }
        }

        return lectors;
    }

    private Set<Lector> getLectorsFromResultSet(ResultSet resultSet) throws SQLException {
        Set<Lector> lectors = new HashSet<>();
        Lector lector;
        while (resultSet.next()) {
            lector = new Lector();
            lector.setId(resultSet.getInt("id"));
            lector.setFirstName(resultSet.getString("first_name"));
            lector.setLastName(resultSet.getString("last_name"));
            lector.setDegree(getDegree(resultSet.getString("degree")));
            lector.setSalary(resultSet.getInt("salary"));
            lectors.add(lector);
        }

        return lectors;
    }

    public Lector findById(Integer id) {
        Lector lector = new Lector();
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM lectors WHERE id = " + id);

            while (resultSet.next()) {
                lector.setId(resultSet.getInt("id"));
                lector.setFirstName(resultSet.getString("first_name"));
                lector.setLastName(resultSet.getString("last_name"));
                lector.setDegree(getDegree(resultSet.getString("degree")));
                lector.setSalary(resultSet.getInt("salary"));
            }
            resultSet.close();
            statement.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(CONNECTION_ERROR_MESSAGE);
            }
        }

        return lector;
    }

    public Set<Lector> findAllWhoContain(String template) {
        Set<Lector> lectors = null;
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM lectors\n" +
                    "WHERE lectors.first_name LIKE '%" + template + "%' " +
                    "OR lectors.last_name LIKE '%" + template + "%';");

            lectors = getLectorsFromResultSet(resultSet);
            resultSet.close();
            statement.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(CONNECTION_ERROR_MESSAGE);
            }
        }

        return lectors;
    }

    private Degree getDegree(String degree) {
        if (degree.equalsIgnoreCase(Degree.ASSISTANT.toString())) {
            return Degree.ASSISTANT;
        } else if (degree.equalsIgnoreCase(Degree.ASSOCIATE_PROFESSOR.toString())) {
            return Degree.ASSOCIATE_PROFESSOR;
        } else if (degree.equalsIgnoreCase(Degree.PROFESSOR.toString())) {
            return Degree.PROFESSOR;
        }

        return null;
    }
}
