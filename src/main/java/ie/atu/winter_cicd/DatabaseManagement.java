package ie.atu.winter_cicd;

import java.sql.*;
public abstract class DatabaseManagement implements DatabaseInterface{
    private Connection connection;
    private PreparedStatement stmt;

    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "password");

    public DatabaseManagement(String url, String username, String pass) throws SQLException
    {
        connection = DriverManager.getConnection(url, username, pass);
    }

    @Override
    public void addStudent(String pName, int pCode, String timeS) throws SQLException {
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
    public void addStudentInfo(int pCode, String classNum, String gender, int year) throws SQLException {
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
                int pCode = resultSet.getInt("pCode");
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
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "password");
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
    public void updateStudent(int pCode, String newForm, String newGender, String newName, String newYear, String newTimeS) throws SQLException {
        String updateForm = "UPDATE student_info SET classNum = ? WHERE pCode = ?";
        String updateGender = "UPDATE student_info SET classNum = ? WHERE pCode = ?";
        String updateName = "UPDATE student_info SET classNum = ? WHERE pCode = ?";
        String updateYear = "UPDATE student_info SET classNum = ? WHERE pCode = ?";
        String updateTimeS = "UPDATE student_info SET classNum = ? WHERE pCode = ?";

        try(PreparedStatement statement = connection.prepareStatement(updateForm)) {
            statement.setString(1, newForm);
            statement.setInt(2, pCode);

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

        try(PreparedStatement statement = connection.prepareStatement(updateGender)) {
            statement.setString(1, newGender);
            statement.setInt(2, pCode);

            int affectedInfo = statement.executeUpdate();
            if(affectedInfo > 0)
            {
                System.out.println("Customer gender updated successfully.");
            }else{
                System.out.println("No customer found with ID: " + pCode);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        try(PreparedStatement statement = connection.prepareStatement(updateName)) {
            statement.setString(1, newName);
            statement.setInt(2, pCode);

            int affectedInfo = statement.executeUpdate();
            if(affectedInfo > 0)
            {
                System.out.println("Customer Name updated successfully.");
            }else{
                System.out.println("No customer found with ID: " + pCode);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        try(PreparedStatement statement = connection.prepareStatement(updateYear)) {
            statement.setString(1, newYear);
            statement.setInt(2, pCode);

            int affectedInfo = statement.executeUpdate();
            if(affectedInfo > 0)
            {
                System.out.println("Customer BirthYear updated successfully.");
            }else{
                System.out.println("No customer found with ID: " + pCode);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        try(PreparedStatement statement = connection.prepareStatement(updateTimeS)) {
            statement.setString(1, newTimeS);
            statement.setInt(2, pCode);

            int affectedInfo = statement.executeUpdate();
            if(affectedInfo > 0)
            {
                System.out.println("Customer TimeStamp updated successfully.");
            }else{
                System.out.println("No customer found with ID: " + pCode);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

