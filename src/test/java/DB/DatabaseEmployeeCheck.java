package DB;

import java.sql.*;

public class DatabaseEmployeeCheck {

    public static boolean EmployeeInDB(int id) throws SQLException {

        String connectionString = "jdbc:postgresql://dpg-cs1a7j5ds78s73b4opi0-a.frankfurt-postgres.render.com/xclients_8hjn";
        String DBUsername = "xclients_user";
        String DBPassword = "Y8KdToh2vSdMnhb22u9OaIRJNyuG7xvA";

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