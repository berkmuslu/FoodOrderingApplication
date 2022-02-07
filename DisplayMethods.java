import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public interface DisplayMethods {

         static void showMenus(int ID){
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

        };

         static void showRestaurantMenus(int ID, String restaurantName, String ownerName) {

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
                Method.editMenu(ID, restaurantName, ownerName);
            } else {
                Restaurant.ownerMenu(ID, restaurantName, ownerName);

            }



        }


    }




