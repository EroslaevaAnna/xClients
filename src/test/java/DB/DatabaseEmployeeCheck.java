package DB;

import java.sql.*;

public class DatabaseEmployeeCheck {

    public static boolean EmployeeInDB(int id) throws SQLException {

        String connectionString = "jdbc:postgresql://dpg-csl69vjv2p9s73b3mda0-a.frankfurt-postgres.render.com/xclients_db";
        String DBUsername = "xclients_user";
        String DBPassword = "oQKb70bPBQI2QHVEGrFOrZF8zxaKUHcv";

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