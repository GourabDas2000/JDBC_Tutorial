import java.sql.*;
class MysqlConnection{
    public static void main(String[] args) {
        Connection con = null;
        Statement statement = null;
        ResultSet result = null;

        try {
            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Database connection details
            String url = DatabaseConfig.url;
            String username = DatabaseConfig.user;
            String password = DatabaseConfig.password;
            String query = "SELECT * FROM gourab";

            // Establish the connection
            con = DriverManager.getConnection(url, username, password);

            // Execute the query
            statement = con.createStatement();
            result = statement.executeQuery(query);

            // Process the results
            // This is for Retriving the query
            while (result.next()) {
                int Age  = result.getInt("Age");
                String Name = result.getString("Name");
                String Occupation = result.getString("Occupation");
                String PhoneNO = result.getString("PhoneNO");

                System.out.printf("Age: %d, Name: %s, Occupation: %s, PhoneNO: %s%n", Age, Name, Occupation, PhoneNO);
            }

        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection or query failed");
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources");
                e.printStackTrace();
            }
        }

    }
}