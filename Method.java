import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Method implements DisplayMethods{

    private static final String resetColor = "\u001B[0m";
    private static final String redColor = "\u001B[31m";
    private static final String greenColor = "\u001B[32m";

    public static void createLine(int Amount) {


        for (int i = 0; i < Amount; i++) {

            System.out.print("-");

        }
        System.out.println();


    }



    public static void createLine(String Text) {


        for (int i = 0; i < Text.length(); i++) {

            System.out.print("-");

        }
        System.out.println();


    }

    public static void createHeader(String Text, int Bottom) {
        System.out.println();
        for (int i = 0; i < Text.length() + 2; i++) {
            System.out.print("-");
        }

        System.out.println("\n|" + Text + "|");

        for (int i = 0; i < Bottom; i++) {
            System.out.print("-");
        }


    }

    public static void createHeader(String Text) {

        for (int i = 0; i < Text.length() + 2; i++) {
            System.out.print("-");
        }

        System.out.println("\n|" + Text + "|");

        for (int i = 0; i < Text.length() + 2; i++) {
            System.out.print("-");
        }


    }

    public static String colorText(String Text, String Color) {

        if (Color.toLowerCase().equals("red")) {
            Color = redColor;

        }

        if (Color.toLowerCase().equals("green")) {
            Color = greenColor;

        }

        String newText = (Color + Text + resetColor);
        return newText;

    }

    public static void freezeScreen() {

        try {

            Thread.sleep(Start.freezeSecond * 1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

    }

    public static void registerMember(String fullname, String username, String pass, String phone, String address) {


        try {

            java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

            String query = " insert into users (fullname, username, password, phone, address, memberdate)"
                    + " values (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = Start.con.prepareStatement(query);
            preparedStmt.setString(1, fullname);
            preparedStmt.setString(2, username);
            preparedStmt.setString(3, pass);
            preparedStmt.setString(4, phone);
            preparedStmt.setString(5, address);
            preparedStmt.setDate(6, date);
            preparedStmt.execute();

            System.out.print(Method.colorText("User " + username + " Created Successfully!", "green"));
            System.out.println();


        } catch (Exception e) {
            createLine(25);
            showDatabaseError();
            createLine(25);
        }

    }

    public static void registerMember(String fullname, String restaurantname, String restaurantusername, String pass, String phone, String address) {

        try {

            java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

            String query = " insert into restaurants (restaurantowner, restaurantname, restaurantusername, restaurantpassword, phone, address, date)"
                    + " values (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = Start.con.prepareStatement(query);
            preparedStmt.setString(1, fullname);
            preparedStmt.setString(2, restaurantname);
            preparedStmt.setString(3, restaurantusername);
            preparedStmt.setString(4, pass);
            preparedStmt.setString(5, phone);
            preparedStmt.setString(6, address);
            preparedStmt.setDate(7, date);

            preparedStmt.execute();

            System.out.print(Method.colorText("Restaurant " + restaurantname + " Created Successfully!", "green"));
            System.out.println();
            System.out.println(Method.colorText("Restaurant Username For Log-In: " + restaurantusername, "green"));


        } catch (Exception e) {
            createLine(25);
            Method.showDatabaseError();
            createLine(25);
        }

    }

    public static void loginPanel() {

        Scanner input = new Scanner(System.in);

        Method.createHeader("Log-In Panel");


        System.out.println("\nEnter Your Username: ");
        String username = input.next();

        if(username.equals("-1")){
            Menu.startMenu();
        }

        System.out.println("Enter Your Password: ");
        String password = input.next();

        if(password.equals("-1")){
            Menu.startMenu();
        }

        PreparedStatement ps;
        ResultSet rs;

        if (username.contains("restaurant_")) {

            String query = "SELECT * FROM restaurants WHERE `restaurantusername` =? AND `restaurantpassword` =?";


            try {

                ps = Start.con.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, password);


                rs = ps.executeQuery();


                if (rs.next()) {

                    String name = rs.getString("restaurantname");
                    String owner = rs.getString("restaurantowner");
                    int id = rs.getInt("restaurantID");
                    Restaurant.ownerMenu(id, name, owner);


                } else {

                    System.err.println("Check Your Username And Password!");
                    Menu.startMenu();

                }

            } catch (Exception e) {

                //System.out.println(e);

            }


        } else {

            String query = "SELECT * FROM users WHERE `username` =? AND `password` =?";

            try {

                ps = Start.con.prepareStatement(query);

                ps.setString(1, username);
                ps.setString(2, password);

                rs = ps.executeQuery();


                if (rs.next()) {

                    int ID = rs.getInt("ID");
                    User.userMenu(username, ID);

                } else {

                    System.err.println("Check Your Username And Password!");
                    Menu.startMenu();

                }

            } catch (Exception e) {

                //System.out.println(e);

            }

        }

    }

    private static int cnt;
    private static String input;

    private static String restusername;

    public static String restaurantUsernameCreator(String restaurantName){
 restusername = "";
        PreparedStatement ps;
        ResultSet rs;

        try
        {

            String query = "SELECT restaurantusername FROM restaurants";

                cnt = 0;

                ps = Start.con.prepareStatement(query);
                rs = ps.executeQuery();



            String restUsername1 = "restaurant_" + restaurantName.replaceAll("\\s","");
            String restn1= restUsername1.replaceAll("ş","s");
            String restn2 = restn1.replaceAll("ç","c");
            String restn3 = restn2.replaceAll("ı","i");
            String restn4 = restn3.replaceAll("ü","u");
            String restUsername = restn4.replaceAll("ö","o");
            restusername = restUsername.toLowerCase();


                while (rs.next()) {

                    if (rs.getString("restaurantusername").contains(restusername)) {
                        cnt++;

                    }
                }

                if(cnt != 0) {
                    restusername = restusername + (cnt+1);
                }

        }catch (Exception e){
            System.out.println(e);
        }

        return restusername;

    }

    public static String checkUsername(String type, int length) {

        Scanner checker = new Scanner(System.in);
        PreparedStatement ps;
        ResultSet rs;



try
    {

        String query = "SELECT username FROM users";

        while (true) {
            cnt = 0;
            System.out.println("Enter Your " + type + ":");
            input = checker.nextLine();

            if (input.equals("-1")) {
                Menu.startMenu();
            }

            ps = Start.con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {

                if (rs.getString("username").equals(input)) {
                    cnt = 1;
                }

            }

            if(cnt == 1){
                System.err.println("ERROR! This username is already taken!");
                continue;
            }

            if (input.length() > length) {
                System.err.println(type + " Can't Be More Than " + length + " Characters!");

            } else if (input.contains("restaurant_")) {
                System.err.println("You Can't Have 'restaurant_' In Your Username!");

            } else {

                break;
            }
        }

    }catch (Exception e){
    System.out.println(e);
    }


        return input;
    }

    public static String checkLength(String type, int length) {

        Scanner checker = new Scanner(System.in);
        String input;


        while (true) {
            System.out.println("Enter Your " + type + ":");
            input = checker.nextLine();

            if (input.equals("-1")) {
                Menu.startMenu();
            }


            if (input.length() > length) {
                System.err.println(type + " Can't Be More Than " + length + " Characters!");

            } else if (type.equals("Username") && input.contains("restaurant_")) {
                System.err.println("You Can't Have 'restaurant_' In Your Username!");

            } else {
                break;
            }

        }

        return input;
    }

    public static void showDatabaseError() {
        System.err.println("Error! Please Check Database Connection!");

    }



    public static void showMenu(int ID) {


        String foodname;
        String fooding;
        int foodid;
        double price;
        String query = "SELECT (groupname) AS Types FROM `groups` WHERE restaurantID = ? AND groupID IN (SELECT menugroup FROM menus WHERE  groups.restaurantID = menus.restaurantID);";
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = Start.con.prepareStatement(query);
            ps.setInt(1, ID);
            rs = ps.executeQuery();

            System.out.println("-----------------------------------------------------------------------------");
            System.out.println("|                                    MENU                                   |");
            System.out.println("-----------------------------------------------------------------------------");


            while (rs.next()) {

                String type = rs.getString("Types");

                Method.createHeader(type, 77);
                System.out.println();

                String query2 = "SELECT DISTINCT(foodname) AS Name,foodingredients AS Ing,foodprice AS Price,foodID as FoodID FROM menus WHERE restaurantID=? AND menugroup IN (SELECT groupID FROM `groups` WHERE groupName = ? )";

                try {
                    PreparedStatement ps1 = Start.con.prepareStatement(query2);
                    ps1.setInt(1, ID);
                    ps1.setString(2, type);

                    ResultSet rs1 = ps1.executeQuery();

                    while (rs1.next()) {
                        foodname = rs1.getString("Name");
                        fooding = rs1.getString("Ing");
                        price = rs1.getDouble("Price");
                        foodid = rs1.getInt("FoodID");

                        System.out.printf("#%-4d %-50s %s %.2f%s", foodid, foodname + "(" + fooding + ")", "\t\t\t", price, " TL");
                        System.out.println();

                    }
                    Method.createLine(77);

                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public static void restaurantShowMenu(int ID, String restaurantName, String ownerName) {

        String foodname;
        String fooding;
        int foodid;
        double price;
        String query = "SELECT (groupname) AS Types FROM `groups` WHERE restaurantID = ? AND groupID IN (SELECT menugroup FROM menus WHERE  groups.restaurantID = menus.restaurantID);";
        PreparedStatement ps;
        ResultSet rs;


        try {
            ps = Start.con.prepareStatement(query);
            ps.setInt(1, ID);
            rs = ps.executeQuery();

            System.out.println("-----------------------------------------------------------------------------");
            System.out.println("|                                    MENU                                   |");
            System.out.println("-----------------------------------------------------------------------------");


            while (rs.next()) {

                String type = rs.getString("Types");

                Method.createHeader(type, 77);
                System.out.println();

                String query2 = "SELECT DISTINCT(foodname) AS Name,foodingredients AS Ing,foodprice AS Price,foodID as FoodID FROM menus WHERE restaurantID=? AND menugroup IN (SELECT groupID FROM `groups` WHERE groupName = ? )";

                try {
                    PreparedStatement ps1 = Start.con.prepareStatement(query2);
                    ps1.setInt(1, ID);
                    ps1.setString(2, type);

                    ResultSet rs1 = ps1.executeQuery();

                    while (rs1.next()) {
                        foodname = rs1.getString("Name");
                        fooding = rs1.getString("Ing");
                        price = rs1.getDouble("Price");
                        foodid = rs1.getInt("FoodID");

                        System.out.printf("#%-4d %-50s %s %.2f%s", foodid, foodname + "(" + fooding + ")", "\t\t\t", price, " TL");
                        System.out.println();

                    }
                    Method.createLine(77);

                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        Scanner inp = new Scanner(System.in);

        Method.createLine(22);
        System.out.println("Enter '1' To Edit Menu");
        System.out.println("Enter '-1' To Return");
        Method.createLine(22);
        System.out.println("Your Choice: ");
        int ans = inp.nextInt();

        if (ans == -1) {
            Restaurant.ownerMenu(ID, restaurantName, ownerName);
        } else if (ans == 1) {
            DisplayMethods.showRestaurantMenus(ID, restaurantName, ownerName);
        } else {
            Restaurant.ownerMenu(ID, restaurantName, ownerName);

        }


    }

    public static void editMenu(int ID, String restaurantName, String ownerName) {

        Scanner inp = new Scanner(System.in);
        Method.createLine("Enter '2' To Edit Food Groups");
        System.out.println("Enter '1' To Edit Food");
        System.out.println("Enter '2' To Edit Food Groups");
        System.out.println("Enter '-1' To Return");
        Method.createLine("Enter '2' To Edit Food Groups");

        System.out.print("Your Choice: ");
        int ans = inp.nextInt();

        switch (ans) {
            case 1:
                editFoodMenu(ID, restaurantName, ownerName);
            case 2:
                editFoodGroup(ID, restaurantName, ownerName);
            case -1:
                restaurantShowMenu(ID, restaurantName, ownerName);

            default:
                editMenu(ID, restaurantName, ownerName);
        }

        System.out.println();


    }

    private static void editFoodMenu(int ID, String restaurantName, String ownerName) {
        Scanner inp = new Scanner(System.in);
        Method.createHeader("Edit Food", 32);
        System.out.println();
        System.out.println("Enter '1' To Add New Food");
        System.out.println("Enter '2' To Remove Food");
        System.out.println("Enter '3' To Edit Food");
        System.out.println("Enter '-1' To Return");
        createLine("Enter '3' To Change Food's Group");
        System.out.print("Your Choice: ");
        int ans = inp.nextInt();


        switch (ans) {
            case 1:
                addFood(ID, restaurantName, ownerName);
            case 2:
                removeFood(ID, restaurantName, ownerName);

            case 3:
                editFood(ID, restaurantName, ownerName);
            case -1:
                editMenu(ID, restaurantName, ownerName);
            default:
                editMenu(ID, restaurantName, ownerName);

        }

    }

    private static void addFood(int ID, String restaurantName, String ownerName) {

        PreparedStatement ps;
        ResultSet rs;
        Scanner inp = new Scanner(System.in);
        Scanner inp1 = new Scanner(System.in);

        String query = "SELECT groupname,groupID FROM `groups` WHERE restaurantID = ? ";


        try {


            Method.createHeader("Add New Food");
            System.out.println();

            System.out.println("(Enter '0' To See Groups)");
            System.out.print("Enter Food's Group ID: #");
            int groupID = inp.nextInt();

            if (groupID == -1) {
                editMenu(ID, restaurantName, ownerName);
            } else if (groupID == 0) {
                ps = Start.con.prepareStatement(query);
                ps.setInt(1, ID);
                rs = ps.executeQuery();

                Method.createHeader("GRUOPS", 26);
                System.out.println();
                while (rs.next()) {
                    System.out.printf("|%-20s(#%s|\n", rs.getString("groupname"), rs.getInt("groupID") + ")");
                }
                Method.createLine(26);

                addFood(ID, restaurantName, ownerName);

            }


            System.out.print("Enter Food's Name: ");
            String name = inp1.nextLine();
            if (name.contains("-1")) {
                editFoodMenu(ID, restaurantName, ownerName);
            }

            System.out.print("Enter Food's Ingredients: ");
            String ing = inp1.nextLine();
            if (ing.contains("-1")) {
                editFoodMenu(ID, restaurantName, ownerName);
            }

            System.out.print("Enter Food's Price: ");
            double price = inp1.nextDouble();
            if (price == -1) {
                editFoodMenu(ID, restaurantName, ownerName);
            }

            String addQuery = "INSERT INTO menus (restaurantID,foodname,foodingredients,foodprice,menugroup) VALUES (?,?,?,?,?)";

            ps = Start.con.prepareStatement(addQuery);

            ps.setInt(1, ID);
            ps.setString(2, name);
            ps.setString(3, ing);
            ps.setDouble(4, price);
            ps.setInt(5, groupID);

            ps.executeUpdate();

            System.out.println(colorText("Food Created Successfully!", "green"));
            editMenu(ID, restaurantName, ownerName);
        } catch (SQLException e) {
            System.out.println(e);
        }


    }

    private static void removeFood(int ID, String restaurantName, String ownerName) {

        PreparedStatement ps;
        ResultSet rs;
        Scanner inp = new Scanner(System.in);
        String query = "SELECT foodname,foodid FROM menus WHERE restaurantID = ?";
        try {
            ps = Start.con.prepareStatement(query);
            ps.setInt(1, ID);
            rs = ps.executeQuery();
            ArrayList<Integer> exist = new ArrayList<>();

            Method.createHeader("Remove Food", 26);
            System.out.println();
            while (rs.next()) {
                System.out.printf("|%-20s(#%s|\n", rs.getString("foodname"), rs.getInt("foodid") + ")");
                exist.add(rs.getInt("foodid"));
            }


            Method.createLine(26);
            while (true) {
                System.out.print("Select Food To Remove: #");
                int removeFood = inp.nextInt();

                for (int i = 0; i < exist.size(); i++) {
                    if (removeFood == exist.get(i)) {

                        String removeQuery = "DELETE FROM menus WHERE restaurantID =? AND foodID =?";
                        ps = Start.con.prepareStatement(removeQuery);
                        ps.setInt(1, ID);
                        ps.setInt(2, removeFood);
                        ps.executeUpdate();
                        System.out.println(Method.colorText("Food Removed Successfully!", "green"));
                        editMenu(ID, restaurantName, ownerName);
                        break;

                    } else {

                        System.err.println("ERROR!");
                        removeFood(ID, restaurantName, ownerName);

                    }
                }

                for (int i = 0; i < exist.size(); i++) {
                    exist.remove(i);
                }
            }


        } catch (Exception e) {

        }

    }

    private static void editFood(int ID, String restaurantName, String ownerName) {

        PreparedStatement ps;
        ResultSet rs;
        Scanner liner = new Scanner(System.in);
        Scanner inp = new Scanner(System.in);
        String query = "SELECT foodname,foodID FROM menus WHERE restaurantID = ?";

        Method.createHeader("Edit Food", 26);
        System.out.println();

        try {

            ps = Start.con.prepareStatement(query);
            ps.setInt(1, ID);
            rs = ps.executeQuery();
            ArrayList<Integer> exist = new ArrayList<>();

            while (rs.next()) {
                System.out.printf("|%-20s(#%s|\n", rs.getString("foodname"), rs.getInt("foodID") + ")");
                exist.add(rs.getInt("foodID"));
            }

            System.out.print("Select Food To Edit: #");
            int editFood = inp.nextInt();
            int firstCnt = 0;
            while (true) {

                for (int i = 0; i < exist.size(); i++) {
                    if (editFood == exist.get(i)) {
                        firstCnt = 0;
                        String selectQuery = "SELECT foodname FROM menus WHERE foodID =? AND restaurantID =?";
                        ps = Start.con.prepareStatement(selectQuery);
                        ps.setInt(1, editFood);
                        ps.setInt(2, ID);
                        rs = ps.executeQuery();
                        rs.next();
                        String selected = rs.getString("foodname");

                        Method.createHeader("Selected Food: " + selected);
                        System.out.println();
                        Method.createLine("Enter '2' To Change Food's Ingredients");
                        System.out.println("Enter '1' To Change Food's Name");
                        System.out.println("Enter '2' To Change Food's Ingredients");
                        System.out.println("Enter '3' To Change Food's Price");
                        System.out.println("Enter '4' To Change Food's Group");
                        System.out.println("Enter '-1' To Return");
                        Method.createLine("Enter '2' To Change Food's Ingredients");
                        System.out.print("Your Choice: ");
                        int edit = inp.nextInt();

                        switch (edit) {
                            case 1:
                                String updateQuery = "UPDATE menus SET foodname = ? WHERE restaurantID = ? AND foodID = ?;";
                                System.out.print("Enter Food's New Name: ");
                                String newName = liner.nextLine();

                                ps = Start.con.prepareStatement(updateQuery);
                                ps.setString(1, newName);
                                ps.setInt(2, ID);
                                ps.setInt(3, editFood);
                                ps.executeUpdate();

                                System.out.println(colorText("Food's Name Is Changed!", "green"));

                                break;
                            case 2:
                                String updateQuery1 = "UPDATE menus SET foodingredients = ? WHERE restaurantID = ? AND foodID = ?;";

                                System.out.print("Enter Food's New Ingredients: ");
                                String newIngredient = liner.nextLine();

                                ps = Start.con.prepareStatement(updateQuery1);
                                ps.setString(1, newIngredient);
                                ps.setInt(2, ID);
                                ps.setInt(3, editFood);
                                System.out.println(colorText("Food's Ingredients Are Changed!", "green"));
                                break;
                            case 3:
                                String updateQuery2 = "UPDATE menus SET foodprice = ? WHERE restaurantID = ? AND foodID = ?;";

                                System.out.print("Enter Food's New Price: ");
                                double newPrice = inp.nextDouble();
                                ps = Start.con.prepareStatement(updateQuery2);
                                ps.setDouble(1, newPrice);
                                ps.setInt(2, ID);
                                ps.setInt(3, editFood);

                                System.out.println(colorText("Food's Price Is Changed!", "green"));
                                break;

                            case 4:
                                String queryGroup = "SELECT groupname,groupID FROM `groups` WHERE restaurantID = ? ";

                                ps = Start.con.prepareStatement(queryGroup);
                                ps.setInt(1, ID);
                                rs = ps.executeQuery();

                                ArrayList<Integer> groups = new ArrayList<>();

                                Method.createHeader("GRUOPS", 26);
                                System.out.println();

                                while (rs.next()) {
                                    System.out.printf("|%-20s(#%s|\n", rs.getString("groupname"), rs.getInt("groupID") + ")");
                                    groups.add(rs.getInt("groupID"));
                                }

                                Method.createLine(26);
                                Method.freezeScreen();

                                System.out.print("Enter Food's New Group: #");
                                int newGroup = inp.nextInt();

                                int cnt = 0;

                                for (int x = 0; x < groups.size(); x++) {

                                    if (newGroup == groups.get(x)) {
                                        String updateQuery3 = "UPDATE menus SET menugroup = ? WHERE restaurantID = ? AND foodID = ?;";
                                        ps = Start.con.prepareStatement(updateQuery3);
                                        ps.setDouble(1, newGroup);
                                        ps.setInt(2, ID);
                                        ps.setInt(3, editFood);
                                        ps.executeUpdate();

                                        System.out.println(colorText("Food's Group Is Changed!", "green"));
                                        cnt = 0;
                                        break;
                                    } else {
                                        cnt++;

                                    }
                                }

                                if (cnt == groups.size()) {
                                    System.err.println("ERROR!");
                                    cnt = 0;
                                }
                                for (int j = 0; j < groups.size(); j++) {
                                    groups.remove(j);
                                }

                            case -1:
                                editFoodMenu(ID, restaurantName, ownerName);
                                break;
                            default:
                        }
                    } else {
                        firstCnt++;
                    }

                    if (firstCnt == exist.size()) {
                        System.err.println("ERROR!");
                        firstCnt = 0;
                    }

                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }


        editFoodMenu(ID, restaurantName, ownerName);


    }

    private static void editFoodGroup(int ID, String restaurantName, String ownerName) {
        Scanner inp = new Scanner(System.in);
        Method.createHeader("Edit Food", 35);
        System.out.println();
        System.out.println("Enter '1' To Create New Food Group");
        System.out.println("Enter '2' To Remove Food Group");
        System.out.println("Enter '3' To Edit Food Group's Name");
        System.out.println("Enter '-1' To Return");
        createLine("Enter '3' To Edit Food Group's Name");
        System.out.print("Your Choice: ");
        int ans = inp.nextInt();

        switch (ans) {
            case 1:
                addFoodGroup(ID, restaurantName, ownerName);
                break;
            case 2:
                removeFoodGroup(ID, restaurantName, ownerName);
                break;
            case 3:
                editFoodGroupName(ID, restaurantName, ownerName);
                break;
            case -1:
                editMenu(ID, restaurantName, ownerName);
                break;


        }


    }

    private static void editFoodGroupName(int ID, String restaurantName, String ownerName) {

        ResultSet rs;
        PreparedStatement ps;
        Scanner inp = new Scanner(System.in);
        String queryGroup = "SELECT groupname,groupID FROM `groups` WHERE restaurantID = ? ";

        try {

            ps = Start.con.prepareStatement(queryGroup);
            ps.setInt(1, ID);
            rs = ps.executeQuery();
            System.out.println("(Enter '0' To See Groups)");
            System.out.print("Enter Group's ID: #");
            int groupid = inp.nextInt();
            if (groupid == 0) {
                Method.createHeader("GROUPS", 26);
                System.out.println();

                while (rs.next()) {
                    System.out.printf("|%-20s(#%s|\n", rs.getString("groupname"), rs.getInt("groupID") + ")");
                }

                Method.createLine(26);
                editFoodGroupName(ID, restaurantName, ownerName);


            }
            System.out.println();
            Method.freezeScreen();
            String query = "SELECT groupname FROM `groups` WHERE restaurantID = ? AND groupID =?";

            ps = Start.con.prepareStatement(query);
            ps.setInt(1, ID);
            ps.setInt(2, groupid);
            rs = ps.executeQuery();
            rs.next();
            String groupname = rs.getString("groupname");

            if (groupid == -1) {
                editFoodGroup(ID, restaurantName, ownerName);
            }


            createHeader("Selected Group: " + groupname);
            System.out.println();
            Scanner liner = new Scanner(System.in);
            System.out.print("Enter New Name: ");
            String newName = liner.nextLine();

            String updateQuery = "UPDATE `groups` SET groupname = ? WHERE restaurantID = ? AND groupID = ?";
            ps = Start.con.prepareStatement(updateQuery);
            ps.setString(1, newName);
            ps.setInt(2, ID);
            ps.setInt(3, groupid);

            ps.executeUpdate();

            System.out.println(colorText("Group's Name Changed Successfully!", "green"));
            editFoodGroup(ID, restaurantName, ownerName);
        } catch (Exception e) {
            System.out.println(e);
            System.err.println("ERROR!");
        }

    }

    private static void addFoodGroup(int ID, String restaurantName, String ownerName) {

        PreparedStatement ps;

        Scanner inp = new Scanner(System.in);
        createHeader("Create New Food Group");
        System.out.println();

        System.out.print("Enter New Food Group's Name: ");
        String groupName = inp.nextLine();

        if (groupName.equals("-1")) {
            editFoodGroup(ID, restaurantName, ownerName);
        }

        String query = "INSERT INTO `groups` (restaurantID, groupname) VALUES (?, ?)";
        try {
            ps = Start.con.prepareStatement(query);
            ps.setInt(1, ID);
            ps.setString(2, groupName);
            ps.executeUpdate();

            System.out.println(colorText("Food Group Created Successfully!", "green"));

            editFoodGroup(ID, restaurantName, ownerName);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void removeFoodGroup(int ID, String restaurantName, String ownerName) {

        PreparedStatement ps;
        ResultSet rs;
        Scanner inp = new Scanner(System.in);
        createHeader("Remove Food Group");
        System.out.println();

        System.out.println("(Enter '0' To See Groups)");
        System.out.print("Enter Food Group's ID: ");
        int groupID = inp.nextInt();

        if (groupID == -1) {
            editFoodGroup(ID, restaurantName, ownerName);
        } else if (groupID == 0) {
            String queryGroup = "SELECT groupname,groupID FROM `groups` WHERE restaurantID = ? ";

            try {

                ps = Start.con.prepareStatement(queryGroup);
                ps.setInt(1, ID);
                rs = ps.executeQuery();


                Method.createHeader("GROUPS", 26);
                System.out.println();

                while (rs.next()) {
                    System.out.printf("|%-20s(#%s|\n", rs.getString("groupname"), rs.getInt("groupID") + ")");
                }

                removeFoodGroup(ID, restaurantName, ownerName);
                Method.createLine(26);
                System.out.println();
                Method.freezeScreen();


            } catch (Exception e) {

            }
        }


        String query = "DELETE FROM `groups` WHERE restaurantID = ? AND groupID = ?";
        String query2 = "DELETE FROM menus WHERE menugroup = ? AND restaurantID =?";
        try {
            ps = Start.con.prepareStatement(query);
            ps.setInt(1, ID);
            ps.setInt(2, groupID);
            ps.executeUpdate();

            ps = Start.con.prepareStatement(query2);
            ps.setInt(2, ID);
            ps.setInt(1, groupID);
            ps.executeUpdate();

            System.out.println(colorText("Food Group Removed Successfully!", "green"));

            editFoodGroup(ID, restaurantName, ownerName);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static String makeSpace(String text, int Total) {

        String length = "";

        Total -= text.length();
        Total--;

        for (int i = 0; i < Total; i++) {
            length += " ";
        }

        return length;

    }

    public static void seeLastOrders(int ID, String restaurantName, String ownerName) {
        createHeader("Last 5 Orders");
        System.out.println();
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT orders.orderID,orders.userID,orders.foodID,orders.amount,orders.price, menus.foodname,menus.foodID,menus.restaurantID, users.fullname,users.phone,users.address,users.ID\n" +
                "FROM orders, menus, users\n" +
                "WHERE menus.restaurantID = ? AND orders.foodID = menus.foodID AND orders.userID = users.ID AND orders.done = 1 ORDER BY orderID DESC;\n";

        try {

            ps = Start.con.prepareStatement(query);
            ps.setInt(1, ID);
            rs = ps.executeQuery();

            int orderCounter = 1;



            while (rs.next() && orderCounter < 6) {

                int order = rs.getInt("orderID");
                int amount = rs.getInt("amount");
                double price = rs.getDouble("price");

                String foodname = rs.getString("foodname");
                String customerName = rs.getString("fullname");
                String address = rs.getString("address");
                String phone = rs.getString("phone");

                System.out.printf("Order ID: %d\nCustomer Name: %s\nCustomer Address: %s\nCustomer Phone: %s\nCustomer Order: %s(%d)\nTotal: %.2f\n", order, customerName, address, phone, foodname, amount, price);
                System.out.println("-----------------------------------");
                orderCounter++;


            }

            freezeScreen();
            Restaurant.ownerMenu(ID, restaurantName, ownerName);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static int justID = 0;
    private static String restName;
    private static String owName;
    public static int prevCount;
    public static boolean flag;


    public static void checkOrders(int ID, String restaurantName, String ownerName, int prev) {
        justID = ID;
        restName = restaurantName;
        owName = ownerName;
        prevCount = prev;

        Scanner inp = new Scanner(System.in);

        createHeader("Check Orders");
        System.out.println();

/*if(inp.nextInt() == -1){
    Restaurant.ownerMenu(ID,restaurantName,ownerName);
}*/



T1 thread = new T1();
flag = true;
thread.start();

        int answer= inp.nextInt();

        switch (answer){
            case 1:
                flag = false;
                doneOrder(justID,restName,owName);
                break;
            case 2:
                flag = false;
                removeOrder(justID,restName,owName);

                break;

            case -1:
                flag = false;
                Restaurant.ownerMenu(justID,restName,owName);
                break;
            default:
                flag = false;
                checkOrders(justID,restName,owName, prevCount);

        }



    }


    public static class T1 extends Thread{

        public void run() {

            while (flag) {


                PreparedStatement ps;
                ResultSet rs;

                String query1 = "Select Count(orderID) from orders WHERE restaurantID = ? AND done = 0";
                String query2 = "SELECT orders.orderID,orders.userID,orders.foodID,orders.amount,orders.price, menus.foodname,menus.foodID,menus.restaurantID, users.fullname,users.phone,users.address,users.ID\n" +
                        "FROM orders, menus, users\n" +
                        "WHERE menus.restaurantID = ? AND orders.foodID = menus.foodID AND orders.userID = users.ID AND orders.done = 0 ORDER BY orders.orderID ASC;\n";


                try {

                    ps = Start.con.prepareStatement(query1);
                    ps.setInt(1,justID);
                    rs = ps.executeQuery();
                    rs.next();
                    int count = rs.getInt(1);

                    if (count != prevCount) {
                        ps = Start.con.prepareStatement(query2);
                        ps.setInt(1,justID);
                        rs = ps.executeQuery();


                        while (rs.next()) {
                            int order = rs.getInt("orderID");
                            int amount = rs.getInt("amount");
                            double price = rs.getDouble("price");

                            String foodname = rs.getString("foodname");
                            String customerName = rs.getString("fullname");
                            String address = rs.getString("address");
                            String phone = rs.getString("phone");

                            System.out.printf("Order ID: %d\nCustomer Name: %s\nCustomer Address: %s\nCustomer Phone: %s\nCustomer Order: %s(%d)\nTotal: %.2f\n", order, customerName, address, phone, foodname, amount, price);
                            System.out.println("-----------------------------------");
                        }

                        System.out.println("Enter '1' To Confirm The Order");
                        System.out.println("Enter '2' To Remove The Order");
                        System.out.println("Enter '-1' To Return!");
                        createLine("Enter '1' To Confirm The Order-----");
                        System.out.print("Your Choice: ");
                        System.out.println();
                    }

                    // System.out.println("Count = " + count + " And prevCount = " + prevCount);

                    prevCount = count;

                } catch (Exception e) {

                }


            }


        }

    }

    public static void doneOrder(int ID,String restaurantName, String ownerName){
        PreparedStatement ps;

        String doneQuery = "UPDATE orders SET done = 1 WHERE orderID =? AND restaurantID =?";
        Scanner inp = new Scanner(System.in);

        System.out.println("Enter Order ID:");
        int orderid = inp.nextInt();

        if(orderid == -1){
            checkOrders(ID,restaurantName,ownerName,0);
        }

        try {



            ps = Start.con.prepareStatement(doneQuery);
            ps.setInt(1,orderid);
            ps.setInt(2,ID);
            ps.executeUpdate();

            System.out.println(colorText("Order is Done!","green"));

            freezeScreen();

            checkOrders(ID,restaurantName,ownerName,0);


        }catch (Exception e){
            System.out.println(e);
            showDatabaseError();
        }

    }

    public static void removeOrder(int ID, String restaurantName, String ownerName){

        PreparedStatement ps;
        String removeQuery = "DELETE FROM orders WHERE orderID =? AND restaurantID =?";

        Scanner inp = new Scanner(System.in);

        System.out.println("Enter Order ID:");
        int orderid = inp.nextInt();

        if(orderid == -1){
            checkOrders(ID,restaurantName,ownerName,0);
        }

        try {

            ps = Start.con.prepareStatement(removeQuery);
            ps.setInt(1,orderid);
            ps.setInt(2,ID);
            ps.executeUpdate();

            System.out.println(colorText("Order is Removed!","green"));

            freezeScreen();

            checkOrders(ID,restaurantName,ownerName,0);


        }catch (Exception e){
            System.out.println(e);
            showDatabaseError();
        }

    }

}
