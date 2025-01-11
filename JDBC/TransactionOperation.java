import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class TransactionOperation {
    public TransactionOperation(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Class loaded Successfully");
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }
    }
    public void Transacion(String debitacc , String creditacc , int ammount){
        String debitQuery = "update Account set balance = balance - ? where account_holder = ?";
        String creditQuery = "update Account set balance = balance + ? where account_holder = ?";
        try {
            Connection con = DriverManager.getConnection(DatabaseConfig.url , DatabaseConfig.user , DatabaseConfig.password);
            con.setAutoCommit(false);
            PreparedStatement debitSt = con.prepareStatement(debitQuery);
            PreparedStatement creditSt = con.prepareStatement(creditQuery);
            debitSt.setInt(1,ammount);
            debitSt.setString(2, debitacc);
            creditSt.setInt(1,ammount);
            creditSt.setString(2,creditacc);
            // creditSt.addBatch();
            // debitSt.addBatch();
            // int[] r = debitSt.executeBatch();                  // if you want to perform bulk of operation
            int debitrow = debitSt.executeUpdate();
            int creditrow = creditSt.executeUpdate();
            if(debitrow == creditrow == true){
                con.commit();
                showData();
            }else{
                System.out.println("Row cannot be updated");
                con.rollback();
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }
    public void showData(){
        String query = "select * from Account";
        try {
            Connection con = DriverManager.getConnection(DatabaseConfig.url, DatabaseConfig.user,DatabaseConfig.password);
            Statement st = con.createStatement();
            ResultSet result = st.executeQuery(query);
            System.out.println("Account Table : ");
            while (result.next()) {
                String name = result.getString("account_holder");
                double ammount = result.getDouble("balance");
                System.out.println("Name : " + name + " || " + "Balance: " + ammount);
            }
            con.close();
            st.close();
            result.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }
    public static void main(String[] args) {
        TransactionOperation t1 = new TransactionOperation();
        t1.showData();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Account you want to perfor debit operation : ");
        String debitacc = sc.nextLine();
        System.out.println("Enter the Account you want to perfor debit operation : ");
        String creditacc = sc.nextLine();
        System.out.println("Enter the Account you want to perfor debit operation : ");
        int ammount = sc.nextInt();
        t1.Transacion(debitacc , creditacc , ammount);
        sc.close();
    }
}
