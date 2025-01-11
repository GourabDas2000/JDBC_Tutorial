import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;

public class ImageUpload {
    String url = DatabaseConfig.url;
    String password = DatabaseConfig.password;
    String user = DatabaseConfig.user;
    public ImageUpload(){
        DatabaseConnection();
    }
    public void DatabaseConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver class Loaded Succesfully"); // For Driver class Loaded Succesfully
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void UploadImage(String path , String name){
        String query = "Insert into images (Image_data , Image_Name) values( ? , ? )";
        
        try{
            FileInputStream FI = new FileInputStream(path);// setting the file 
            Connection con = DriverManager.getConnection(url, user, password);// Connecting to Database
            System.out.println("Database is Successfully Connected");
            byte[] bytes = FI.readAllBytes();
            PreparedStatement prestate = con.prepareStatement(query);
            prestate.setBytes(1, bytes);//setting the parameter "?"
            prestate.setString(2, name);
            int rowSpan = prestate.executeUpdate();
            if(rowSpan>0){
                System.out.println("Image Uploaded into the Database Successfully.");
            }else{
                System.out.println("Image cannot be uploaded.Try again Later");
            }
            prestate.close();
            con.close();
            FI.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void FetchImage(String outputPath){
        Scanner sc =  new Scanner(System.in);
        String q1 = "select Image_id , Image_Name from images";
        String q2 = "select Image_data from images where Image_id = ?";
        try{
            Connection con = DriverManager.getConnection(url, user, password);// Connecting to Database
            System.out.println("Database is Successfully Connected");
            Statement st = con.createStatement();
            ResultSet result = st.executeQuery(q1);//showing the database that what image is already present in the database
            while (result.next()) {
                int id = result.getInt("Image_id");
                String name  = result.getString("Image_Name");
                System.out.println("ID -> " + id + " Name -> " + name);
            }
            System.out.println("Select ID from the given ID to Download that image");
            int id = sc.nextInt();
            try {
                FileOutputStream FO = new FileOutputStream(outputPath);// setting the file path and storing into the buffer
                PreparedStatement prestate = con.prepareStatement(q2);
                prestate.setInt(1, id);
                ResultSet innerresu = prestate.executeQuery();
                if (innerresu.next()) { // Move the cursor to the first row
                    byte[] bytes = innerresu.getBytes("Image_data");
                    FO.write(bytes);
                    System.out.println("File is successfully uploaded in the local folder.");
                } else {
                    System.out.println("No image found with the given ID.");
                }
                innerresu.close();
                prestate.close();
                FO.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            con.close();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
       sc.close();
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ImageUpload obj = new ImageUpload();
        String path = "D:/Vscode/java_Application/JDBC/upload/test1.png";
        String outerpath = "D:/Vscode/java_Application/JDBC/upload/fetch/test1.png";
        // System.out.println("Type the Image name you want to save it : ");
        // String name = sc.nextLine();
        // obj.UploadImage(path, name);
        obj.FetchImage(path);
        sc.close();
    }
}
