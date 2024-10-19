package DB;

import java.sql.*;

public class DatabaseEmployeeCheck {

    public static boolean EmployeeInDB(int id) throws SQLException {

        String connectionString = "jdbc:postgresql://dpg-crgp14o8fa8c73aritj0-a.frankfurt-postgres.render.com/x_clients_db_75hr";
        String DBUsername = "x_clients_user";
        String DBPassword = "ypYaT7FBULZv2VxrJuOHVoe78MEElWlb";

        try (Connection connection = DriverManager.getConnection(connectionString, DBUsername, DBPassword)) {
            String SQL = "SELECT COUNT(*) FROM employee WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}