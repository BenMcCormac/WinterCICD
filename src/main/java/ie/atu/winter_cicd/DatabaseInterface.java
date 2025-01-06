package ie.atu.winter_cicd;

import java.sql.SQLException;

public interface DatabaseInterface {
    void addStudent(int pCode, String pName, String timeS) throws SQLException;
    void addStudent(String pName, int pCode, String timeS) throws SQLException;
    void addStudentInfo(int pCode, String classNum, String gender, int year) throws SQLException;
    void connectionTest() throws SQLException;
    void deleteStudentData(int pCode) throws SQLException;void updateStudent(int pCode, String newForm, String newGender, String newName, String newYear, String newTimeS) throws SQLException;
    String getData() throws SQLException;
}
