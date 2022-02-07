import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Scanner;

public class User {

    private static int loginCounter = 0;

    public static void userMenu(String userName, int ID) {

if(loginCounter == 0) {
    Method.createHeader("Welcome, " + userName);
loginCounter++;
}


        Scanner input = new Scanner(System.in);

        while (true) {
            Method.freezeScreen();
            Method.createHeader("User Menu", 33);
            System.out.println("\nEnter '1' To Order Food");
            System.out.println("Enter '2' to See Profile");
            System.out.println("Enter '-1' to Return Log-In Panel");
            Method.createLine(33);
            System.out.print("Your Choice: ");
            int answer = input.nextInt();

            switch (answer) {

                case 1:
                    orderMenu(userName, ID);

                    break;

                case 2:
                    userProfile(ID);
                    break;

                case -1:
                    loginCounter = 0;
                    Menu.startMenu();
                    break;

                default:
                    continue;
            }
        }
    }

    private static void userProfile(int userID) {

        Scanner input = new Scanner(System.in);

        PreparedStatement ps;
        ResultSet rs;

        String fullname, username, password, phoneNumber, address;
        int totalOrder;
        double totalMoney;


        String query = "SELECT * FROM users WHERE `ID` =?";

        try {

            ps = Start.con.prepareStatement(query);

            ps.setString(1, Integer.toString(userID));

            rs = ps.executeQuery();


            if (rs.next()) {

                int ID = rs.getInt("ID");

                fullname = rs.getString("fullname");
                username = rs.getString("username");
                password = rs.getString("password");
                phoneNumber = rs.getString("phone");
                address = rs.getString("address");
                totalOrder = rs.getInt("totalorder");
                totalMoney = rs.getDouble("totalspending");


                while (true) {
                    Method.freezeScreen();
                    System.out.println();
                    Method.createHeader("User Profile");
                    System.out.println();
                    System.out.println("Full Name: " + fullname);
                    System.out.println("Username: " + username);
                    System.out.println("Password: " + password);
                    System.out.println("Phone Number: " + phoneNumber);
                    System.out.println("Address: " + address);
                    System.out.println("Total Order: " + totalOrder);
                    System.out.println("Money Spent So Far: " + totalMoney + " TL");
                    Method.freezeScreen();

                    Method.createLine(30);
                    System.out.println("Enter '1' to Edit Phone Number");
                    System.out.println("Enter '2' to Edit Address");
                    System.out.println("Enter '-1' to Return");
                    Method.createLine(30);
                    System.out.print("Your Choice: ");

                    int answer = input.nextInt();

                    switch (answer) {

                        case 1:
                            editPhoneNumber(userID, phoneNumber);
                            break;
                        case 2:
                            editAddress(userID, address);
                            break;
                        case -1:
                            userMenu(username, userID);
                        default:
                            continue;
                    }

                }


            }


        } catch (Exception e) {
            System.out.println(e);
        }


    }

    private static void editPhoneNumber(int userID, String oldPhoneNumber) {


        Scanner input = new Scanner(System.in);

        PreparedStatement ps;
        String newPhoneNumber;


        while (true) {

            System.out.println("Please Enter New Phone Number: ");
            newPhoneNumber = input.next();

            if (newPhoneNumber.equals("-1")) {
                userProfile(userID);
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

            String query = "update users set phone = ? where ID = ?";
            ps = Start.con.prepareStatement(query);
            ps.setString(1, newPhoneNumber);
            ps.setString(2, Integer.toString(userID));
            ps.executeUpdate();


            System.out.println(Method.colorText("Phone Number Changed To " + newPhoneNumber, "green"));
            Method.freezeScreen();

            userProfile(userID);


        } catch (Exception e) {
            Method.showDatabaseError();
        }

    }

    private static void editAddress(int userID, String oldAddress) {
        PreparedStatement ps;
        Scanner input1 = new Scanner(System.in);
        String newAddress;
        while (true) {
            System.out.println("Please Enter New Address: ");
            newAddress = input1.nextLine();

            if (newAddress.equals(oldAddress)) {
                System.err.println("New Address Can't Be The Same With Old Address");
            } else if (newAddress.equals("-1")) {
                userProfile(userID);
            } else {
                break;

            }
        }


        try {

            String query = "update users set address = ? where ID = ?";
            ps = Start.con.prepareStatement(query);
            ps.setString(1, newAddress);
            ps.setString(2, Integer.toString(userID));
            ps.executeUpdate();

            System.out.println(Method.colorText("Address Changed To " + newAddress, "green"));
            Method.freezeScreen();

            userProfile(userID);


        } catch (Exception e) {
            Method.showDatabaseError();
        }

    }

    public static void searchRestaurant(String username, int userID) {
        Scanner inp = new Scanner(System.in);

        PreparedStatement ps;
        ResultSet rs;
        Method.createHeader("Search Restaurant");
        System.out.println();
        System.out.println("Enter A Property Of The Restaurant");
        String property = inp.nextLine();
property = property.toLowerCase();

        String query = "SELECT * FROM restaurants WHERE LOWER( restaurants.restaurantname ) LIKE ? OR restaurantID =?";
        String query1 = "SELECT COUNT(*) AS Count FROM restaurants WHERE LOWER( restaurants.restaurantname ) LIKE ? OR restaurantID =?";

        try {

            ps = Start.con.prepareStatement(query1);
            ps.setString(1, "%"  + property + "%");
            ps.setString(2, property);
            rs = ps.executeQuery();
            rs.next();

            int count = rs.getInt("Count");

            if(count == 0 || count < 0){
                {
                    Method.createHeader("There Is No Match!");
                    System.out.println();
                    userMenu(username,userID);

                }
            }else if (count == 1) {
                Method.createHeader("There Is 1 Restaurant");
            } else {
                Method.createHeader("There Are " + count + " Restaurans");
            }

            ps = Start.con.prepareStatement(query);
            ps.setString(1, "%"  + property + "%");
            ps.setString(2, property);

            rs = ps.executeQuery();


            System.out.println();
            System.out.printf("%-13s %s %-23s %s %-25s %s %-23s", "ID", "\t", " Restaurant Name", "\t", "Restaurant Number", "\t\t\t", " Restaurant Address");
            System.out.println();
            System.out.printf("%-13s %s %-23s %s %-25s %s %-23s", "---", "\t ", "---------------", "\t", "-----------------", "\t\t\t ", "---------------------------");
            System.out.println();

            while (rs.next()) {

                System.out.printf("#%-13s %s %-23s %s %-25s %s %-23s", rs.getInt("restaurantID"), "\t ", rs.getString("restaurantname"), "\t", rs.getString("phone"), "\t\t\t ", rs.getString("address"));
                System.out.println();
            }




        } catch (Exception e) {
            System.out.println(e);
        }


        while (true){

            System.out.println();
            Method.createLine(30);
            System.out.println("Enter '1' To Choose Restaurant");
            System.out.println("Enter '-1' To Return");
            Method.createLine(30);
            System.out.print("Your Choice: ");

            int ans = inp.nextInt();

            switch (ans) {
                case 1:
                    try {
                        String countQuery = "SELECT COUNT(restaurantID) AS restCount FROM restaurants";
                        ps = Start.con.prepareStatement(countQuery);
                        rs = ps.executeQuery();
                        rs.next();

                        int count = rs.getInt("restCount");


                        System.out.print("Enter Restaurant ID: #");
                        int id = inp.nextInt();

                        if (id == -1) {

                            orderMenu(username,userID);
                        }else if(id > count) {
                            System.out.println("Check Restaurant ID!");
                        }
                        else
                        {

                            chosenRestaurant(id,username,userID);
                        }

                        break;

                    }catch (Exception e){
                        System.out.println(e);
                    }

                    break;
                case -1:

                    orderMenu(username,userID);
                    break;
                default:


            }

        }


    }

    public static void orderMenu(String userName,int ID) {

        Scanner input = new Scanner(System.in);

        while (true) {

            Method.freezeScreen();

            Method.createHeader("Order Menu", 30);
            System.out.println("\nEnter '1' to List Restaurants");
            System.out.println("Enter '2' to Search Restaurant");
            System.out.println("Enter '3' to Search Food");
            System.out.println("Enter '-1' to Return User Menu");
            Method.createLine(30);
            System.out.print("Your Choice: ");
            int answer = input.nextInt();


            switch (answer) {

                case 1:
                    listRestaurants(userName,ID);

                    break;


                case 2:
                    searchRestaurant(userName,ID);

                    break;
                case 3:
                    searchFood(userName,ID);

                    break;
                case -1:
                    userMenu(userName,ID);
                    break;

                default:
                    continue;


            }


        }


    }

    public static void listRestaurants(String username,int ID) {


        PreparedStatement ps;
        ResultSet rs;
        int count;

        String query = "SELECT MAX(restaurantID) AS Count FROM restaurants";
        String query1 = "SELECT * FROM restaurants";

        try {
            ps = Start.con.prepareStatement(query);
            rs = ps.executeQuery();
            rs.next();
            count = rs.getInt("Count");
            System.out.println();

            if(count == 0 || count < 0){
                {
                    Method.createHeader("There Is No Match!");
                    System.out.println();
userMenu(username,ID);
                }
            }else if (count == 1) {
                Method.createHeader("There Is 1 Restaurant");
            } else {
                Method.createHeader("There Are " + count + " Restaurans");
            }


            System.out.println();
            System.out.printf("%-13s %s %-23s %s %-25s %s %-23s", "ID", "\t", " Restaurant Name", "\t", "Restaurant Number", "\t\t\t", " Restaurant Address");
            System.out.println();
            System.out.printf("%-13s %s %-23s %s %-25s %s %-23s", "---", "\t ", "---------------", "\t", "-----------------", "\t\t\t ", "---------------------------");
            System.out.println();


        }catch (Exception e){


            System.out.println(e);


        }

        try {

            ps = Start.con.prepareStatement(query1);
            rs = ps.executeQuery();

            while (rs.next()){

                System.out.printf("#%-13s %s %-23s %s %-25s %s %-23s", rs.getInt("restaurantID"), "\t ", rs.getString("restaurantname"), "\t", rs.getString("phone"), "\t\t\t ", rs.getString("address"));
                System.out.println();
            }


        }catch (Exception e){
            System.out.println(e);
        }


while (true){
    Scanner inp = new Scanner(System.in);
    System.out.println();
    Method.createLine(30);
    System.out.println("Enter '1' To Choose Restaurant");
    System.out.println("Enter '-1' To Return");
    Method.createLine(30);
    System.out.print("Your Choice: ");

    int ans = inp.nextInt();

    switch (ans) {
        case 1:
try {
    ps = Start.con.prepareStatement(query);
    rs = ps.executeQuery();
    rs.next();
    count = rs.getInt("Count");

    System.out.print("Enter Restaurant ID: #");
    int id = inp.nextInt();

    if (id == -1) {

    }else if(id > count){

        System.err.println("ID Is Out Of Range!");
    }else {

        chosenRestaurant(id,username,ID);
    }

    break;

}catch (Exception e){
    System.out.println(e);
}

            break;
        case -1:

            orderMenu(username,ID);
            break;
            default:


    }

}


    }

    public static void searchFood(String username, int userID) {


        int tempID;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter The Property Of The Food:");
        String property = input.nextLine();
        PreparedStatement ps;
        ResultSet rs;
        property = property.toLowerCase();

        String realquery = "SELECT restaurants.restaurantname AS restaurantname , menus.* FROM restaurants INNER JOIN menus ON restaurants.restaurantID = menus.restaurantID AND LOWER(menus.foodname) LIKE ?";
        try {

            ps = Start.con.prepareStatement(realquery);
            ps.setString(1, "%"  + property + "%");
            rs = ps.executeQuery();


/*
            if(rs.next()){


            }

            */

tempID = -1;

            while (rs.next()) {

               if(tempID != rs.getInt("restaurantID")){
                    System.out.println();
                    System.out.printf("%-13s", rs.getString("restaurantname") + "(#" + rs.getInt("restaurantID") + ")");
                    System.out.println();
                    Method.createLine(rs.getString("restaurantname") + "(#" + rs.getInt("restaurantID") + ")");

                   System.out.printf("#%-4d %-50s %s %.2f%s", rs.getInt("foodID"), rs.getString("foodname") + "(" + rs.getString("foodingredients") + ")" , "\t", rs.getDouble("foodprice"),"TL");
                   System.out.println();


               }else{


                    System.out.printf("#%-4d %-50s %s %.2f%s", rs.getInt("foodID"), rs.getString("foodname") + "(" + rs.getString("foodingredients") + ")" , "\t", rs.getDouble("foodprice"),"TL");
                    System.out.println();
                }

                tempID = rs.getInt("restaurantID");



            }


        } catch (Exception e) {
            System.out.println(e);
        }



        while (true){
            Scanner inp = new Scanner(System.in);
            System.out.println();
            Method.createLine(30);
            System.out.println("Enter '1' To Choose Restaurant");
            System.out.println("Enter '-1' To Return");
            Method.createLine(30);
            System.out.print("Your Choice: ");

            int ans = inp.nextInt();

            switch (ans) {
                case 1:
                    try {
String countQuery = "SELECT COUNT(restaurantID) AS restCount FROM restaurants";
ps = Start.con.prepareStatement(countQuery);
rs = ps.executeQuery();
rs.next();

int count = rs.getInt("restCount");


                        System.out.print("Enter Restaurant ID: #");
                        int id = inp.nextInt();

                        if (id == -1) {

orderMenu(username,userID);
                        }else if(id > count) {
                            System.out.println("Check Restaurant ID!");
                        }
                            else
                         {

                            chosenRestaurant(id,username,userID);
                        }

                        break;

                    }catch (Exception e){
                        System.out.println(e);
                    }

                    break;
                case -1:

                    orderMenu(username,userID);
                    break;
                default:


            }

        }


    }

    public static void chosenRestaurant(int ID,String username,int userID){

        String restname;
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM restaurants WHERE `restaurantID` =?";

        Scanner inp = new Scanner(System.in);
        try {


            ps = Start.con.prepareStatement(query);
            ps.setInt(1, ID);
            rs = ps.executeQuery();
rs.next();

                restname = rs.getString("restaurantname");

            Method.createLine("Chosen Restaurant:   " +restname);
            System.out.println("|Chosen Restaurant: " +restname + "|");



            while(true){
                Method.createLine("Chosen Restaurant:   " +restname);
                System.out.println("Enter '1' To See Menu");
                System.out.println("Enter '2' To Order");
                System.out.println("Enter '-1' To Return");
                Method.createLine("Chosen Restaurant:   " +restname);
                System.out.print("Your Choice: ");
                int ans = inp.nextInt();

                switch (ans){

                    case 1:
                        DisplayMethods.showMenus(ID);

                        break;
                    case 2:
                        orderFood(userID,username,ID);

                        break;
                    case -1:
                        userMenu(username,userID);
break;
                    default:


                }

            }




        }catch (Exception e) {
//            System.out.println(e);
        }



    }

    public static void orderFood(int id,String username,int restID){
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

        PreparedStatement ps;
        ResultSet rs;
        Scanner inp = new Scanner(System.in);
        System.out.print("Enter Food ID: #");
int foodid = inp.nextInt();
if (foodid == -1){
    userMenu(username,id);
}

String query = "SELECT * FROM menus WHERE foodID =?";
String query1 = "SELECT * FROM users WHERE username =?";
try {
    ps = Start.con.prepareStatement(query);
    ps.setInt(1,foodid);
    rs = ps.executeQuery();
    rs.next();
    String foodname = rs.getString("foodname");


    Method.createHeader("Selected Food: " + foodname);

    System.out.print("\nEnter Amount:");
    int amount = inp.nextInt();

    if(amount < 0){
        orderFood(id,username,restID);
    }else{

        double price;
        String query2 = "INSERT INTO orders (restaurantID,userID,foodID,amount,price,date) VALUES (?,?,?,?,?,?)";


        price = rs.getDouble("foodprice");

        price *= amount;

        ps = Start.con.prepareStatement(query1);
        ps.setString(1,username);
        rs = ps.executeQuery();
        rs.next();

        int userID = rs.getInt("ID");
        String fullname = rs.getString("fullname");
        String address = rs.getString("address");
        String phone = rs.getString("phone");
        int totalorder = rs.getInt("totalorder");
        double totalspending = rs.getDouble("totalspending");

        Method.createLine(59);
        System.out.println("|                Order Confirmation Screen                |");
        Method.createLine(59);
        System.out.println("|Full Name: " + fullname + Method.makeSpace("|Full Name: " + fullname,59) + "|");
        System.out.println("|Address: " + address + Method.makeSpace("|Address: " + address,59) + "|");
        System.out.println("|Phone Number: " + phone +  Method.makeSpace("|Phone Number: " + phone,59) + "|");
        Method.createLine(59);
        System.out.println("|Selected Food: " + foodname + "(" + amount + ")" + Method.makeSpace("|Selected Food: " + foodname + "(" + amount + ")",59) + "|");
        System.out.println("|Price: "  + price + " TL" + Method.makeSpace("|Price: "  + price + " TL",59) + "|");
        Method.createLine(59);
        System.out.println("|Enter 1 To Confirm" + Method.makeSpace("Enter 1 To Confirm",58) + "|");
        System.out.println("|Enter -1 To Cancel" + Method.makeSpace("Enter -1 To Cancel",58) + "|");
        Method.createLine(59);
        System.out.print("Your Choice: ");
int ans = inp.nextInt();

        if(ans == 1){
            totalorder++;
            totalspending+= price;

            ps = Start.con.prepareStatement(query2);
            ps.setInt(1,restID);
            ps.setInt(2,userID);
            ps.setInt(3,foodid);
            ps.setInt(4,amount);
            ps.setDouble(5,price);
            ps.setDate(6,date);
            ps.executeUpdate();

            String query4 = "SELECT * FROM orders WHERE OrderID IN (SELECT MAX(OrderID) FROM orders WHERE userID = ?);";
            ps = Start.con.prepareStatement(query4);
            ps.setInt(1,userID);
            rs = ps.executeQuery();
            rs.next();
int orderID = rs.getInt("orderID");
            String query3 = "UPDATE users SET totalorder = ? , totalspending = ? WHERE ID = ?;";

ps = Start.con.prepareStatement(query3);
ps.setInt(1,totalorder);
ps.setDouble(2,totalspending);
ps.setInt(3,userID);
ps.executeUpdate();

Method.freezeScreen();
            System.out.println(Method.colorText("Order#" + orderID + " Is Ordered Succesfully!","green"));

            userMenu(username,userID);


        }



    }




}catch (Exception e){
    System.out.println(e);
}







    }


}