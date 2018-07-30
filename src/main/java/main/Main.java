package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        Properties properties = new Properties();
        properties.setProperty("user", "Lemetriss");
        properties.setProperty("password", "123");
        properties.setProperty("useUnicode", "true");
        properties.setProperty("characterEncoding", "utf8");
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mainAcad", properties);) {

            deleteStudent(connection);

//            updateStudent(connection);

//            Student student = new Student(0, "Vasya", "Lomachenko", 30, "12:00:00");
//            addStudent(student,connection);

//            List<Student> students = getStudents(connection);
//            students.forEach(System.out::println);
//            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Удаляем строку по фамилии Иванов
    private void deleteStudent(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM mainacad.student WHERE lastName = ?");
        preparedStatement.setString(1, "Ivanov");
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    private void updateStudent(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE mainacad.student SET name = ?, age = ? WHERE name = ?");
        preparedStatement.setString(1, "Alexander");
        preparedStatement.setInt(2, 19);
        preparedStatement.setString(3, "Sergey");
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    private void addStudent(Student student, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO mainacad.student (name, lastName, age, time) VALUES (?, ?, ?, ?)");
        preparedStatement.setString(1, student.getName());
        preparedStatement.setString(2, student.getLastName());
        preparedStatement.setInt(3, student.getAge());
        preparedStatement.setString(4, student.getTime());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


    private List<Student> getStudents(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM student");
        List<Student> students = new ArrayList<>();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String lastName = resultSet.getString("lastName");
            int age = resultSet.getInt("age");
            String time = resultSet.getString("time");
            students.add(new Student(id,name,lastName,age,time));
        }
        return students;
    }
}
