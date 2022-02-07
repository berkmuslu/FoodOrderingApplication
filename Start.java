import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Start {

    public static final String name = "FoodPack";
    public static final int freezeSecond = 0;
    public static boolean isConnected = false;
    public static Connection con;

    public static void main(String[] args){


        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://<host>:<port>/<db-name>?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey&useSSL=false&allowPublicKeyRetrieval=true","<username>","<password>");
            isConnected = true;

        }catch(Exception e){
            isConnected = false;
            System.out.println(e);
            }

        Method.createHeader("Welcome To FoodPack");
        Menu.startMenu();


    }
    }
