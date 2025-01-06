package ie.atu.winter_cicd;

import java.sql.SQLException;

public interface DatabaseInterface {
    void addStudent(String pCode, String pName, String timeS) throws SQLException;
    void addStudentInfo(String classNum, String gender, int year) throws SQLException;
    void connectionTest() throws SQLException;
    void deleteStudentData(int pCode) throws SQLException;
    void updateStudentForm(int pCode, String newForm) throws SQLException;
    void updateStudentGender(int pCode, String newGender) throws SQLException;
    String getData() throws SQLException;
    void updateStudentForm(String pCode, String newForm) throws SQLException;
    void updateStudentGender(String pCode, String gender) throws SQLException;
}
