import java.util.Scanner;
public class Menu {

    public static void startMenu(){

        Scanner input = new Scanner(System.in);

        while(true){
            Method.freezeScreen();
            Method.createHeader("Main Menu", 38);

            System.out.println("\nEnter '1' To Log-In");
            System.out.println("Enter '2' to Register");
            System.out.println("Enter '3' to Check Database Connection");
            System.out.println("Enter '4' to See Credits");
            System.out.println("Enter '-1' to Exit");

            Method.createLine(38);
System.out.print("Your Choice: ");
            int answer = input.nextInt();

            switch (answer){

                case 1:
                    Method.freezeScreen();

                    Method.loginPanel();
                    break;

                case 2:
                    Method.freezeScreen();
                    Method.createHeader("Register Panel", 41);
                    System.out.println("\nEnter '1' to Register As User");
                    System.out.println("Enter '2' to Register As Restaurant Owner");
                    System.out.println("Enter '-1' to Return Log-In Panel");
                    Method.createLine(41);

                    System.out.print("Your Choice: ");
                    int ans2 = input.nextInt();

                    switch (ans2){

                        case 1:
                            registerMenu(1);
                            break;

                        case 2:

                            registerMenu(2);
break;

                        case -1:
                            startMenu();
break;
                        default:
                            continue;

                    }

                case 3:
                    serverStatus();
                    break;

                case 4:
                    Credits();

                case -1:
                    System.out.println("Good Bye!");
                    Method.freezeScreen();

System.exit(1);
                    break;

                    default:
                        continue;


            }


        }

    }

    public static void registerMenu(int userType){

        Scanner input = new Scanner(System.in);


if(userType == 1){

    String fullName;
    String userName;
    String password;
    String address;
    String phone;

    Method.createHeader("Register a New User");
    System.out.println();

    fullName = (Method.checkLength("Full Name",30));


    userName = (Method.checkUsername("Username",15));


    password = (Method.checkLength("Password",15));


    while(true){
        System.out.println("Enter Your Phone Number: ");
        phone = input.next();

        if(phone.equals("-1")){
            startMenu();
        }

        if(phone.length() != 10 && !phone.matches("[0-9]+")) {

            System.err.println("Phone Number Must Have 10 Digits!");
            System.err.println("Phone Number Must Only Have Digits!");

        }
        else if(phone.length() != 10){
            System.err.println("Phone Number Must Have 10 Digits!");

        } else if(!phone.matches("[0-9]+")){
            System.err.println("Phone Number Must Only Have Digits!");

        }else{
            break;

        }

    }

    address = (Method.checkLength("Address",255));

    Method.freezeScreen();


    Method.registerMember(fullName,userName,password,phone,address);
    startMenu();
}

if(userType == 2){

    String fullName;
    String restaurantName;
    String password;
    String address;
    String phone;

    Method.createHeader("Register a New Restaurant Owner");

    System.out.println();
    fullName = (Method.checkLength("Full Name",30));
    restaurantName = (Method.checkLength("Restaurant Name",50));
    password = (Method.checkLength("Password",15));

    while(true){
    System.out.println("Enter Your Phone Number: ");
    phone = input.next();

    if(phone.equals("-1")){
        startMenu();
    }

    if(phone.length() != 10 && !phone.matches("[0-9]+")) {

        System.err.println("Phone Number Must Have 10 Digits!");
        System.err.println("Phone Number Must Only Have Digits!");

    }
    else if(phone.length() != 10){
        System.err.println("Phone Number Must Have 10 Digits!");

    } else if(!phone.matches("[0-9]+")){
        System.err.println("Phone Number Must Only Have Digits!");
    }else{
        break;

    }
}
     address = (Method.checkLength("Address",255));


    Method.freezeScreen();

    String restaurantUserName = Method.restaurantUsernameCreator(restaurantName);

    Method.registerMember(fullName,restaurantName,restaurantUserName,password,phone,address);
    startMenu();

}

    }

    public static void serverStatus(){

        Method.createHeader("DATABASE STATUS",30);

        if(Start.isConnected){

            System.out.println("\n|Database Connection : " + Method.colorText("OK!","green") + "   |");

        }else{

            System.out.println("\n|Database Connection : " + Method.colorText("ERROR!","red") + "|");

        }

        Method.createLine(30);
        Method.freezeScreen();
        startMenu();

    }

    public static void Credits(){
        Method.createHeader("Credits",35);
        System.out.println();
        System.out.println("Project Name: " + Start.name);
        System.out.println("-----------------------------------");
        System.out.println("Lecture: SE-116");
        System.out.println("-----------------------------------");
        System.out.println("Creators: \nBerk Muslu \nÖmer Yakup Cankurtaran");
        System.out.println("-----------------------------------");
        Method.freezeScreen();
        startMenu();

    }

}
