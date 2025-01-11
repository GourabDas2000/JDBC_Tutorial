import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PrepaiDstatement {
    private static final String url = DatabaseConfig.url;
    private static final String password = DatabaseConfig.password;
    private static final String user = DatabaseConfig.user;
    public void PrepaidStatement(){
        connectWithDatabase();
    }
    public void connectWithDatabase(){ // For Loading the jar file
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Database is succesfully Connected");
        }catch(ClassNotFoundException e){
            System.out.println("Database cannot be connected");
            e.printStackTrace();
        }
    }
    public void showTable(String database){
        try{
            Connection con = DriverManager.getConnection(url,user,password);//Connecting with Database (sql)
            String query = "select * from gourab where age = ?";
            PreparedStatement sentence = con.prepareStatement(query);
            // sentence.setString(1, database);
            sentence.setInt(1, 32);
            ResultSet result = sentence.executeQuery();
            while(result.next()) {
                int age = result.getInt("Age");
                System.out.println(age);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
class ImplementPrepaidClass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PrepaiDstatement obj = new PrepaiDstatement();
        String database = sc.next();
        obj.showTable(database);
        sc.close();
    }
}