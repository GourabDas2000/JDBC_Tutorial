import java.lang.UnsupportedOperationException;

public class DatabaseConfig {
    public static final String url = "jdbc:mysql://localhost:3306/gourab?allowPublicKeyRetrieval=true&trustServerCertificate=true";
    public static final String password = "#Gourabdas10122000";
    public static final String user = "root";
    public DatabaseConfig(){
        throw new UnsupportedOperationException("This is a class that cannot be Initialized");
    }
}
