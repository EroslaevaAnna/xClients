package DB;

import java.sql.*;

public class DatabaseEmployeeCheck {

    public static boolean isEmployeeInDB(int id) {
        String connectionString = "jdbc:postgresql://dpg-crgp14o8fa8c73aritj0-a.frankfurt-postgres.render.com/x_clients_db_75hr";
        String username = "x_clients_user";
        String password = "ypYaT7FBULZv2VxrJuOHVoe78MEElWlb";

        try (Connection connection = DriverManager.getConnection(connectionString, username, password)) {
            String query = "SELECT * FROM employee WHERE id = " + id;
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);

                return resultSet.next();  // Вернет true, если запись существует
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
//    private final String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM employee WHERE id = ?";
//
//    public ResultSet getEmployeeByID(Connection connection, int idCompany) throws SQLException {
//        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID);
//        preparedStatement.setInt(1, idCompany);
//
//        return preparedStatement.executeQuery();
//    }
//
//    public Connection connectToDB() throws SQLException {
//        String connectionString = ConfHelper.getProperty("connectionString");
//        String DBUsername = ConfHelper.getProperty("DBUsername");
//        String DBPassword = ConfHelper.getProperty("DBPassword");
//
//        return DriverManager.getConnection(connectionString, DBUsername, DBPassword);
//    }
//}