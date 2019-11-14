package ua.andrii.verkholiak.repository;

import ua.andrii.verkholiak.entity.Department;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DepartmentRepository {

    private static final String CONNECTION_ERROR_MESSAGE = "Connection error!";
    private static DepartmentRepository INSTANCE;
    private Connection connection;

    private DepartmentRepository() {
        connection = DataBaseConnector.getConnection();
    }

    public static DepartmentRepository getDepartmentRepository() {
        if (INSTANCE == null) {
            INSTANCE = new DepartmentRepository();
        }

        return INSTANCE;
    }

    public Department findByName(String departmentName) {
        Department department = null;
        Integer headOfDepartmentId = null;
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM departments WHERE name = " + "\'" + departmentName + "\'");

            while (resultSet.next()) {
                department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("name"));
                headOfDepartmentId = resultSet.getInt("head_department_id");
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

        if (department == null) {
            return null;
        }

        department.setHeadOfDepartment(LectorRepository.getLectorRepository().findById(headOfDepartmentId));
        return department;
    }

    public double getAverageSalaryForDepartment(String departmentName) {
        double averageSalary = -1;
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT AVG(DISTINCT lectors.salary)\n" +
                    "FROM   departments\n" +
                    "       INNER JOIN department_lector\n" +
                    "         ON department_lector.department_id = departments.id\n" +
                    "       INNER JOIN lectors\n" +
                    "         ON lectors.id = department_lector.lector_id\n" +
                    "WHERE  departments.name = " + "\'" + departmentName + "\'");

            if (resultSet.next()) {
                averageSalary = resultSet.getDouble(1);
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

        return averageSalary;
    }

    public long countLectorsInDepartment(String departmentName) {
        long amountOfLectors = 0;
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(DISTINCT lectors.salary)\n" +
                    "FROM   departments\n" +
                    "       INNER JOIN department_lector\n" +
                    "         ON department_lector.department_id = departments.id\n" +
                    "       INNER JOIN lectors\n" +
                    "         ON lectors.id = department_lector.lector_id\n" +
                    "WHERE  departments.name = " + "\'" + departmentName + "\'");

            if (resultSet.next()) {
                amountOfLectors = resultSet.getLong(1);
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

        return amountOfLectors;
    }
}
