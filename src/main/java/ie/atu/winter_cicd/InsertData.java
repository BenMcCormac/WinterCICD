package ie.atu.winter_cicd;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.sql.*;

public class InsertData {

    public static void main(String[] args) throws SQLException {

        // Connect to the database
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartphones", "root", "password");

        try {


            // Insert a new record into the "Person" table
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO person (pCode, pName, timeS) VALUES (?, ?, ?)");
            stmt.setInt(1, getLastInsertId(conn));
            stmt.setString(2, "Ben McCormack");
            stmt.setString(3, "11:59");
            stmt.executeUpdate();

            // Insert a new record into the "emails" table, referencing the new user
            stmt = conn.prepareStatement("INSERT INTO student_info (pCode, gender, year, classNum) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, getLastInsertId(conn));
            stmt.setString(3, "2003");
            stmt.setString(4, "3B");
            stmt.setString(2, "Male");
            stmt.executeUpdate();

            System.out.println("Insert completed successfully.");
        } catch (SQLException ex) {

            System.out.println("Record insert failed.");
            ex.printStackTrace();
        }
        // Close the connection
        conn.close();
    }

    // Helper method to get the ID of the last inserted record
    private static int getLastInsertId(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
        rs.next();
        int id = rs.getInt(1);
        rs.close();
        stmt.close();
        return id;
    }
}