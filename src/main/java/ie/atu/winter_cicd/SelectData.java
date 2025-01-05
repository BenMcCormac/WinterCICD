package ie.atu.winter_cicd;

import java.sql.*;

public class SelectData {
    public static void main(String[] args) {
        // MySQL database connection details
        String url = "jdbc:mysql://localhost:3306/smartphones";
        String username = "root";
        String password = "password";

        // SQL statement
        String selectSQL = "SELECT i.time, s.pName, i.year, i.classNum, i.gender " +
                "FROM student s " +
                "JOIN student_info i ON s.pCode = i.pCode";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            while (resultSet.next()) {
                String pName = resultSet.getString("pName");
                String time = resultSet.getString("time");
                int year = resultSet.getInt("year");
                String classNum = resultSet.getString("classNum");
                String gender = resultSet.getString("gender");

                System.out.println("Name: " + pName + ", time: " + time + ", year: " + year + ", Form No.: " + classNum + ", gender" + gender);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
