import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Restaurant {

    private static int loginCounter = 0;

    public static void ownerMenu(int ID,String restaurantName,String restaurantOwner){

        if(loginCounter == 0) {
            Method.createHeader("Welcome, " + restaurantOwner);
            System.out.println();
            Method.createHeader("Restaurant Name: " + restaurantName);

            loginCounter++;
        }


        Scanner input = new Scanner(System.in);

        while (true) {
            Method.freezeScreen();

            Method.createHeader("Owner Menu", 33);
            System.out.println("\nEnter '1' To See Orders");
            System.out.println("Enter '2' To See Menu");
            System.out.println("Enter '3' to See Last 5 Orders");
            System.out.println("Enter '4' to See Restaurant Stats");
            System.out.println("Enter '-1' to Return Log-In Panel");

            Method.createLine(33);
            System.out.print("Your Choice: ");
            int answer = input.nextInt();

            switch (answer) {

                case 1:

                    Method.checkOrders(ID,restaurantName,restaurantOwner,0);
                    break;

                case 2:
                    Method.restaurantShowMenu(ID,restaurantName,restaurantOwner);
                    break;

                case 3:
Method.seeLastOrders(ID,restaurantName,restaurantOwner);
                    break;
                case 4:
                    resturantStats(ID,restaurantName);
                    break;
                case -1:
                    loginCounter = 0;
                    Menu.startMenu();
                    break;

            }

        }



    }

    private static void resturantStats(int restID,String restName) {
double price = 0;
        PreparedStatement ps;
        ResultSet rs;
String query1 = "SELECT COUNT(*) FROM orders WHERE restaurantID = ?";
        String query = "SELECT * FROM restaurants WHERE restaurantID = ?";
        String query2 = "SELECT price FROM orders WHERE restaurantID = ? AND done = 1";
        try {
            ps = Start.con.prepareStatement(query);
            ps.setInt(1, restID);
            rs = ps.executeQuery();
            rs.next();
           String restowner = rs.getString("restaurantowner");
String phone = rs.getString("phone");
            String address = rs.getString("address");

            ps = Start.con.prepareStatement(query1);
            ps.setInt(1,restID);
            rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            ps = Start.con.prepareStatement(query2);
            ps.setInt(1, restID);
rs = ps.executeQuery();
            while (rs.next()){
                price += rs.getDouble("price");
            }

            Method.freezeScreen();
            System.out.println();
            Method.createHeader("Restaurant Stats");
            System.out.println();
            System.out.println("Restaurant Name: " + restName);
            System.out.println("Owner: " + restowner);
            System.out.println("Phone Number: " + phone);
            System.out.println("Address: " + address);
            System.out.println("All Orders So Far: " + count);
            System.out.println("All Income So Far: " + price + " TL");
            Method.freezeScreen();

            Method.createLine(30);
            System.out.println("Enter '1' to Edit Phone Number");
            System.out.println("Enter '2' to Edit Address");
            System.out.println("Enter '-1' to Return");
            Method.createLine(30);
            System.out.print("Your Choice: ");

            Scanner input = new Scanner(System.in);

            int answer = input.nextInt();

            switch (answer) {

                case 1:
                    editPhoneNumber(restID, restName,restowner,phone);
                    break;
                case 2:
                    editAddress(restID, restName,restowner,address);
                    break;
                case -1:
ownerMenu(restID,restName,restowner);
                    break;
                default:
resturantStats(restID,restName);
            }

        }catch (Exception e){
            System.out.println(e);
        }



    }

    private static void editAddress(int restID, String restName,String restOwner ,String oldAddress) {
        PreparedStatement ps;
        Scanner input1 = new Scanner(System.in);
        String newAddress;
        while (true) {
            System.out.println("Please Enter New Address: ");
            newAddress = input1.nextLine();

            if (newAddress.equals(oldAddress)) {
                System.err.println("New Address Can't Be The Same With Old Address");
            } else if (newAddress.equals("-1")) {
                ownerMenu(restID,restName,restOwner);
            } else {
                break;

            }
        }


        try {

            String query = "update restaurants set address = ? where restaurantID = ?";
            ps = Start.con.prepareStatement(query);
            ps.setString(1, newAddress);
            ps.setString(2, Integer.toString(restID));
            ps.executeUpdate();

            System.out.println(Method.colorText("Address Changed To " + newAddress, "green"));
            Method.freezeScreen();

            resturantStats(restID,restName);


        } catch (Exception e) {
            Method.showDatabaseError();
        }

    }

    private static void editPhoneNumber(int restID, String restName,String restOwner ,String oldPhoneNumber) {


        Scanner input = new Scanner(System.in);

        PreparedStatement ps;
        String newPhoneNumber;


        while (true) {

            System.out.println("Please Enter New Phone Number: ");
            newPhoneNumber = input.next();

            if (newPhoneNumber.equals("-1")) {
                ownerMenu(restID,restName,restOwner);
                            }


            if (oldPhoneNumber.equals(newPhoneNumber)) {
                System.err.println("The New Phone Number Can't Be The Same With Old Phone Number!");
            } else if (newPhoneNumber.length() != 10 && !newPhoneNumber.matches("[0-9]+")) {

                System.err.println("Phone Number Must Have 10 Digits!");
                System.err.println("Phone Number Must Only Have Digits!");

            } else if (newPhoneNumber.length() != 10) {
                System.err.println("Phone Number Must Have 10 Digits!");

            } else if (!newPhoneNumber.matches("[0-9]+")) {
                System.err.println("Phone Number Must Only Have Digits!");

            } else {
                break;

            }

        }

        try {

            String query = "update restaurants set phone = ? where restaurantID = ?";
            ps = Start.con.prepareStatement(query);
            ps.setString(1, newPhoneNumber);
            ps.setString(2, Integer.toString(restID));
            ps.executeUpdate();


            System.out.println(Method.colorText("Phone Number Changed To " + newPhoneNumber, "green"));
            Method.freezeScreen();

            resturantStats(restID,restName);


        } catch (Exception e) {
            Method.showDatabaseError();
        }

    }


}
