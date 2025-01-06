package ie.atu.winter_cicd;

import java.sql.*;
public class DatabaseManagement implements DatabaseInterface{
    private Connection connection;
    private PreparedStatement stmt;

    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "password");

    public DatabaseManagement(String url, String username, String pass) throws SQLException
    {
        connection = DriverManager.getConnection(url, username, pass);
    }

    @Override
    public void addStudent(String pName, String pCode, String timeS) throws SQLException {
        try {

            // Insert a new record into the "device" table
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO person (pCode, timeS, pName) VALUES (?, ?, ?)");
            stmt.setInt(1, getLastInsertId(conn));
            stmt.setString(3, pName);
            stmt.setString(2, timeS);
            stmt.executeUpdate();

            System.out.println("Insert completed successfully.");
        } catch (SQLException ex) {

            System.out.println("Record insert failed.");
            ex.printStackTrace();
        }
    }

    @Override
    public void addStudentInfo(String classNum, String gender, int year) throws SQLException {
        try {

            stmt = connection.prepareStatement("INSERT INTO studet_info (pCode, gender, classNum, year) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, getLastInsertId(connection));
            stmt.setString(3, classNum);
            stmt.setString(2, gender);
            stmt.setInt(4, year);

            stmt.executeUpdate();

            System.out.println("Insert completed successfully.");
        } catch (SQLException ex) {

            System.out.println("Record insert failed.");
            ex.printStackTrace();
        }
    }

    @Override
    public String getData() throws SQLException {
        StringBuilder resultBuilder = new StringBuilder();

        String selectSQL = "SELECT * FROM person p " +
                "JOIN student_info s ON p.pCode = s.pCode";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            while (resultSet.next()) {
                String pCode = resultSet.getString("pCode");
                String pName = resultSet.getString("pName");
                String gender = resultSet.getString("gender");
                String classNum = resultSet.getString("classNum");
                int year = resultSet.getInt("year");

                //resultBuilder ensures all data from the SQL statement is displayed and not just the most recent
                resultBuilder.append(pCode).append(" ").append(pName).append(" ").append(gender).append(" ").append(classNum).append(" ").append(year).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultBuilder.toString();
    }

    @Override
    public void connectionTest() throws SQLException {
        try
        {
            // Load the driver class ---------------- update for new database
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Create a connection to the database, hardcoding values for now.
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartphones", "root", "password");
            System.out.println("Connection made to connection pool");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //finds last modified info
    private static int getLastInsertId(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
        rs.next();
        int id = rs.getInt(1);
        rs.close();
        stmt.close();
        return id;
    }

    @Override
    public void deleteStudentData(int pCode) throws SQLException {  //using transactions to wrap DELETE statement to ensure all or nothing
        String deletePersonSQL = "DELETE FROM person WHERE pCode = ?" ;
        String deleteStudentSQL = "DELETE FROM student_info WHERE pCode = ?";

        try {
            connection.setAutoCommit(false); // Start transaction
            try (PreparedStatement statement1 = connection.prepareStatement(deletePersonSQL);
                 PreparedStatement statement2 = connection.prepareStatement(deleteStudentSQL)) {
                statement1.setInt(1, pCode);
                statement2.setInt(1, pCode);

                int affectedData1 = statement1.executeUpdate();
                int affectedData2 = statement2.executeUpdate();

                if (affectedData1 > 0 && affectedData2 > 0)
                {
                    System.out.println("Customer with ID " + pCode + " deleted");
                }else
                {
                    System.out.println("No customer found with ID: " + pCode);
                }

                connection.commit(); // Commit transaction
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction on error
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true); // Reset auto-commit mode
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void updateStudentForm(int pCode, String newForm) throws SQLException {

    }

    @Override
    public void updateStudentGender(int pCode, String newGender) throws SQLException {

    }

    @Override
    public void updateStudentForm(String pCode, String newForm) throws SQLException {
        String updateSQL = "UPDATE student_info SET classNum = ? WHERE pCode = ?";

        try(PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            statement.setString(1, newForm);
            statement.setString(2, pCode);

            int affectedInfo = statement.executeUpdate();
            if(affectedInfo > 0)
            {
                System.out.println("Customer form updated successfully.");
            }else{
                System.out.println("No customer found with ID: " + pCode);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateStudentGender(String pCode, String gender) throws SQLException {
        String updateSQL = "UPDATE student_info SET gender = ? WHERE pCode = ?";

        try(PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            statement.setString(1, gender);
            statement.setString(2, pCode);

            int affectedInfo = statement.executeUpdate();
            if(affectedInfo > 0)
            {
                System.out.println("Student gender updated successfully.");
            }else{
                System.out.println("No customer found with ID: " + pCode);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

