import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.lang.ClassNotFoundException;

public class CRUDoperationSQL {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Database is Connected Succesfully");
        }catch(ClassNotFoundException e){
            System.out.println("Database cannot be connect.Check your Connection method");
            e.printStackTrace();
        }
        String url = DatabaseConfig.url;
        String password = DatabaseConfig.password;
        String user = DatabaseConfig.user;
        String InsertQuery = "INSERT INTO gourab VALUES (50, 'Chetan', 'Stand-up-Comedian', '22222222')";
        String UpdateQuery = "update gourab set PhoneNO = 000 where Age = 32 ";
        String DeleteQuery = "DELETE from gourab where Age = 50";
        Connection con = null;
        Statement statement = null;
        ResultSet result = null;
        int rowsEffected = 0;
        try{
            con = DriverManager.getConnection(url,user,password);
            statement = con.createStatement();
            // rowsEffected = statement.executeUpdate(InsertQuery);
            // rowsEffected = statement.executeUpdate(DeleteQuery);
            rowsEffected = statement.executeUpdate(UpdateQuery);

            if(rowsEffected > 0){
                // System.out.println("Successfully  rows Inserted");
                // System.out.println("Successfully  rows Deleted");
                System.out.println("Successfully  rows Updated");

            }
        }catch(SQLException e){
            System.out.println("Query cannot be execute");
            e.printStackTrace();
        }
    }
}
