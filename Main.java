//Name: Victor Vu
//netID: vhv180000

//Project #4 demonstrates the use of hashing to hold onto a database of customer
// information like usernames, passwords, and ordering information. This project
// was most likely intended to have a private class within the main file, but I
// did not know about it at the time of starting this project and revolved it
// around string manipulation. Along side the use of a hashmap included by java,
// it also demonstrates a comprehensive professional application.

import java.util.*;
import java.io.*;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException
    {
        String Row_choice = "",
               Seat_choice = "",
               best_choice,
               Order = "";

        int adult_tickets = 0,
            child_tickets = 0,
            senior_tickets = 0,
            reservation_Count,
            out;

        Auditorium A_choice = null;

        boolean available,
                user_exit,
                admin_exit,
                program_exit = false;

        Node<Seat> best_seat;

        int incorrect;

        String name_user,
               password_user;

        String user_choice;

        Scanner user_input = new Scanner(System.in);

        final double ADULT_P = 10.0,
                CHILD_P = 5.0,
                SENIOR_P = 7.5;

        Scanner User_DB;
        HashMap<String, String> Data_Base = new HashMap<>();

        try
        {
            User_DB = new Scanner(new File("userdb.dat"));
        }
        catch (FileNotFoundException E)
        {
            System.out.println("The user data base file could not be opened.");
            System.out.println("The program is ending.");
            return;
        }

        //This while loop is used to read in the formatted line to then
        // process the line to be put into the User data-base hashmap
        while (User_DB.hasNext())
        {
            String user_line,
                   user_key,
                   user_password;

            user_line = User_DB.nextLine();

            user_key = user_line.substring(0,user_line.indexOf(' '));
            user_password = user_line.substring(user_line.indexOf(' ') + 1);

            Data_Base.put(user_key, user_password);

        }
        User_DB.close();

        Auditorium A1 = new Auditorium("A1.txt");
        Auditorium A2 = new Auditorium("A2.txt");
        Auditorium A3 = new Auditorium("A3.txt");

        //This while loop takes care of when the program should end which is
        // when the admin decides with the selection of 3 in the admin menu
        while (!program_exit)
        {
            System.out.println("Enter your user-name.");
            name_user = user_input.nextLine();

            //This if takes care of input validation for if
            // the username exists in the hashmap data-base
            if (Data_Base.containsKey(name_user))
            {
                incorrect = 0;

                //This while takes care of the validation of the
                // password that is connected to the valid user
                while(incorrect != 3)
                {
                    System.out.println("Enter your password.");
                    password_user = user_input.nextLine();

                    //If the user has orders in their account it goes into this to test the password
                    if (Data_Base.get(name_user).contains("\n"))
                    {
                        if (Data_Base.get(name_user).substring(0, Data_Base.get(name_user).indexOf('\n')).compareTo(password_user) != 0)
                        {
                            incorrect++;
                            System.out.println("Invalid password");
                            System.out.println("You have " + (3 - incorrect) + " attempt(s) left");
                        }
                        else
                        {
                            incorrect = 0;
                            break;
                        }
                    }
                    //If the user does not have orders it goes into this to test the password
                    else
                    {
                        if (Data_Base.get(name_user).compareTo(password_user) != 0)
                        {
                            incorrect++;
                            System.out.println("Invalid password");
                            System.out.println("You have " + (3 - incorrect) + " attempt(s) left");
                        }
                        else
                        {
                            incorrect = 0;
                            break;
                        }
                    }
                }

                //If the user is an admin it goes into the admin UI
                if (name_user.compareTo("admin") == 0)
                {
                    if (incorrect != 3)
                    {
                        admin_exit = false;
                        while (!admin_exit)
                        {
                            System.out.println("1. Print Report");
                            System.out.println("2. Logout");
                            System.out.println("3. Exit");

                            user_choice = user_input.nextLine();

                            //This is if the user wants to print a report as the admin
                            if (user_choice.compareTo("1") == 0)
                            {
                                Node<Seat> info;

                                int a_count = 0,
                                        c_count = 0,
                                        s_count = 0,
                                        open_count = 0,
                                        auditorium_reserved;

                                double price_auditorium;

                                int total_a_count = 0,
                                        total_c_count = 0,
                                        total_s_count = 0,
                                        total_open_count = 0,
                                        total_reserved = 0;

                                double total_price = 0;


                                //This gets all the information from auditorium 1
                                for (int y = 1; y <= A1.getRow_count(); y++)
                                {
                                    for (int x = 1; x <= A1.getSeat_count(); x++)
                                    {
                                        info = A1.getIndex(x,y);

                                        switch (info.getPayload().getTicket_Type())
                                        {
                                            case 'A': a_count++;
                                                break;
                                            case 'C': c_count++;
                                                break;
                                            case 'S': s_count++;
                                                break;
                                            case '.': open_count++;
                                                break;
                                        }
                                    }
                                }

                                //Below this is the processing for auditorium 1 to
                                // then be accumulated to the total in the end
                                price_auditorium = a_count * ADULT_P + c_count * CHILD_P + s_count * SENIOR_P;
                                auditorium_reserved = a_count + c_count + s_count;

                                System.out.println("Auditorium 1\t" + open_count + "\t" + auditorium_reserved + "\t" + a_count + "\t" + c_count + "\t" + s_count + "\t" + "$" + String.format("%.2f", price_auditorium));

                                total_a_count += a_count;
                                total_c_count += c_count;
                                total_s_count += s_count;
                                total_open_count += open_count;
                                total_price += price_auditorium;
                                total_reserved += auditorium_reserved;

                                //This resets the counters to do the processing for auditorium 2
                                a_count = 0;
                                c_count = 0;
                                s_count = 0;
                                open_count = 0;

                                for (int y = 1; y <= A2.getRow_count(); y++)
                                {
                                    for (int x = 1; x <= A2.getSeat_count(); x++)
                                    {
                                        info = A2.getIndex(x,y);

                                        switch (info.getPayload().getTicket_Type())
                                        {
                                            case 'A': a_count++;
                                                break;
                                            case 'C': c_count++;
                                                break;
                                            case 'S': s_count++;
                                                break;
                                            case '.': open_count++;
                                                break;
                                        }
                                    }
                                }

                                price_auditorium = a_count * ADULT_P + c_count * CHILD_P + s_count * SENIOR_P;
                                auditorium_reserved = a_count + c_count + s_count;

                                System.out.println("Auditorium 2\t" + open_count + "\t" + auditorium_reserved + "\t" + a_count + "\t" + c_count + "\t" + s_count + "\t" + "$" + String.format("%.2f", price_auditorium));

                                total_a_count += a_count;
                                total_c_count += c_count;
                                total_s_count += s_count;
                                total_open_count += open_count;
                                total_price += price_auditorium;
                                total_reserved += auditorium_reserved;

                                //Again this resets the counters to process auditorium 3
                                a_count = 0;
                                c_count = 0;
                                s_count = 0;
                                open_count = 0;

                                for (int y = 1; y <= A3.getRow_count(); y++)
                                {
                                    for (int x = 1; x <= A3.getSeat_count(); x++)
                                    {
                                        info = A3.getIndex(x,y);

                                        switch (info.getPayload().getTicket_Type())
                                        {
                                            case 'A': a_count++;
                                                break;
                                            case 'C': c_count++;
                                                break;
                                            case 'S': s_count++;
                                                break;
                                            case '.': open_count++;
                                                break;
                                        }
                                    }
                                }

                                price_auditorium = a_count * ADULT_P + c_count * CHILD_P + s_count * SENIOR_P;
                                auditorium_reserved = a_count + c_count + s_count;

                                System.out.println("Auditorium 3\t" + open_count + "\t" + auditorium_reserved + "\t" + a_count + "\t" + c_count + "\t" + s_count + "\t" + "$" + String.format("%.2f", price_auditorium));

                                total_a_count += a_count;
                                total_c_count += c_count;
                                total_s_count += s_count;
                                total_open_count += open_count;
                                total_price += price_auditorium;
                                total_reserved += auditorium_reserved;

                                System.out.println("Total\t\t" + total_open_count + "\t" + total_reserved + "\t" + total_a_count + "\t" + total_c_count + "\t" + total_s_count + "\t" + "$" + String.format("%.2f", total_price));
                            }
                            //This is to log out of the admin user
                            else if (user_choice.compareTo("2") == 0)
                            {
                                System.out.println("Logging out of user: " + name_user);
                                System.out.println("Have a nice day!");

                                admin_exit = true;
                            }
                            //This is to end the program when the admin decides to do so
                            // and then exports all the auditoriums into their final files
                            else if (user_choice.compareTo("3") == 0)
                            {
                                PrintWriter outputFS = new PrintWriter(new File("A1Final.txt"));

                                for (int y = 1; y <= A1.getRow_count(); y++)
                                {
                                    for (int x = 1; x <= A1.getSeat_count(); x++)
                                        outputFS.print(A1.getIndex(x,y).getPayload().getTicket_Type());

                                    outputFS.println();
                                }
                                outputFS.close();

                                outputFS = new PrintWriter(new File("A2Final.txt"));

                                for (int y = 1; y <= A2.getRow_count(); y++)
                                {
                                    for (int x = 1; x <= A2.getSeat_count(); x++)
                                        outputFS.print(A2.getIndex(x,y).getPayload().getTicket_Type());

                                    outputFS.println();
                                }
                                outputFS.close();

                                outputFS = new PrintWriter(new File("A3Final.txt"));

                                for (int y = 1; y <= A3.getRow_count(); y++)
                                {
                                    for (int x = 1; x <= A3.getSeat_count(); x++)
                                        outputFS.print(A3.getIndex(x,y).getPayload().getTicket_Type());

                                    outputFS.println();
                                }
                                outputFS.close();

                                System.out.println("The program is now ending");
                                admin_exit = true;
                                program_exit = true;
                            }
                            else
                            {
                                System.out.println("Menu input was not valid ");
                            }

                            System.out.println();
                        }

                    }
                }
                //This else is goes into the user UI if they are in the hashmap
                else
                {
                    if (incorrect != 3)
                    {
                        user_exit = false;
                        while (!user_exit)
                        {
                            System.out.println("1. Reserve Seats");
                            System.out.println("2. View Orders");
                            System.out.println("3. Update Orders");
                            System.out.println("4. Display Receipt");
                            System.out.println("5. Log Out");

                            user_choice = user_input.nextLine();

                            //This if is to go through the reservation process in project 0 and project 2
                            if (user_choice.compareTo("1") == 0)
                            {
                                System.out.println("1. Auditorium 1");
                                System.out.println("2. Auditorium 2");
                                System.out.println("3. Auditorium 3");

                                user_choice = user_input.nextLine();

                                if (user_choice.compareTo("1") == 0)
                                {
                                    display_Chart(A1);
                                    A_choice = A1;
                                    Order = "\nAuditorium 1, ";
                                }
                                else if (user_choice.compareTo("2") == 0)
                                {
                                    display_Chart(A2);
                                    A_choice = A2;
                                    Order = "\nAuditorium 2, ";
                                }
                                else if (user_choice.compareTo("3") == 0)
                                {
                                    display_Chart(A3);
                                    A_choice = A3;
                                    Order = "\nAuditorium 3, ";
                                }
                                else
                                {
                                    System.out.println("The auditorium input is invalid");
                                }

                                //Input validation for the row choice input
                                out = 0;
                                while (out != 1)
                                {
                                    try
                                    {
                                        System.out.println("Enter row number of choice.");
                                        Row_choice = user_input.nextLine();

                                        if (Row_choice.length() > 1)
                                            throw new IOException();

                                        if (Integer.parseInt(Row_choice) <= 0 || Integer.parseInt(Row_choice) > A_choice.getRow_count())
                                            throw new IOException();

                                        out++;
                                    }
                                    catch(Exception ToyStory)
                                    {
                                        System.out.println("The input is invalid");
                                    }
                                }

                                //Input validation for the seat choice input
                                out = 0;
                                while (out != 1)
                                {
                                    try
                                    {
                                        System.out.println("Enter seat letter of choice.");
                                        Seat_choice = user_input.nextLine();

                                        if (Seat_choice.length() > 1)
                                            throw new IOException();

                                        if (!(Seat_choice.charAt(0) >= 'A') || !(Seat_choice.charAt(0) <= A_choice.getIndex(A_choice.getSeat_count(), 1).getPayload().getSeat()))
                                            if(!(Seat_choice.charAt(0) >= 'a') || !(Seat_choice.charAt(0) <= Character.toLowerCase(A_choice.getIndex(A_choice.getSeat_count(), 1).getPayload().getSeat())))
                                                throw new IOException();

                                        out++;
                                    }
                                    catch(Exception ToyStory)
                                    {
                                        System.out.println("The input is invalid");
                                    }
                                }

                                //Input validation for the number of adult tickets
                                out = 0;
                                while (out != 1)
                                {
                                    try
                                    {
                                        System.out.println("Enter the number of adult tickets(s) to be reserved.");
                                        adult_tickets = user_input.nextInt();

                                        if (adult_tickets < 0)
                                            throw new IOException();

                                        out++;
                                    }
                                    catch(Exception ToyStory)
                                    {
                                        System.out.println("The input is invalid");
                                    }

                                    user_input.nextLine();
                                }

                                //Input validation for the number of child tickets
                                out = 0;
                                while (out != 1)
                                {
                                    try
                                    {
                                        System.out.println("Enter the number of child tickets(s) to be reserved.");
                                        child_tickets = user_input.nextInt();

                                        if (child_tickets < 0)
                                            throw new IOException();

                                        out++;
                                    }
                                    catch(Exception ToyStory)
                                    {
                                        System.out.println("The input is invalid");
                                    }

                                    user_input.nextLine();
                                }

                                //Input validation for the number of senior tickets
                                out = 0;
                                while (out != 1)
                                {
                                    try
                                    {
                                        System.out.println("Enter the number of senior tickets(s) to be reserved.");
                                        senior_tickets = user_input.nextInt();

                                        if (senior_tickets < 0) {
                                            throw new IOException();
                                        }

                                        out++;
                                    }
                                    catch(Exception ToyStory)
                                    {
                                        System.out.println("The input is invalid");
                                    }

                                    user_input.nextLine();
                                }

                                //This holds a local variable of the number of tickets needed for easier processing
                                reservation_Count = adult_tickets + child_tickets + senior_tickets;

                                //This boolean holds decision of if the reservation is available
                                available = Availability(A_choice, Row_choice, Seat_choice, reservation_Count);

                                //If the reservation is available then it reserves the seats for the user, with
                                // a modified order section that will add the order to the user in the hashmap
                                if (available)
                                {
                                    System.out.println("The requested seats have been reserved");
                                    Reserve(A_choice, Row_choice, Seat_choice, adult_tickets, child_tickets, senior_tickets);

                                    for (int x = 0; x < reservation_Count; x++)
                                    {
                                        if (x + 1 == reservation_Count)
                                            Order += Row_choice + (char)(Seat_choice.toUpperCase().charAt(0) + x);
                                        else
                                            Order += Row_choice + (char)(Seat_choice.toUpperCase().charAt(0) + x) + ",";
                                    }

                                    //This modified order section that will add the order to the user in the hashmap
                                    Order += "\n";

                                    Order += adult_tickets + " adult, ";
                                    Order += child_tickets + " child, ";
                                    Order += senior_tickets + " senior";

                                    Data_Base.put(name_user, Data_Base.get(name_user) + Order);
                                }

                                //If the reservation is not available then it
                                // calculates the best seat in the selected auditorium
                                else
                                {
                                    best_seat = Best_available(A_choice, reservation_Count);

                                    if (best_seat == null)
                                        System.out.println("no seats available");
                                    else
                                    {
                                        //This is triggered for a different output to the console due to only needing one seat
                                        if (reservation_Count == 1)
                                        {
                                            System.out.println("The best available seat is " + best_seat.getPayload().getRow() + best_seat.getPayload().getSeat());
                                            System.out.println("Would you like to reserve it? (Y/N)");
                                        }
                                        //This is triggered for everything else
                                        else
                                        {
                                            System.out.println("The best available seats are " + best_seat.getPayload().getRow() + best_seat.getPayload().getSeat() + " - " + best_seat.getPayload().getRow() + (char)(best_seat.getPayload().getSeat() + reservation_Count - 1));
                                            System.out.println("Would you like to reserve these seats? (Y/N)");
                                        }

                                        best_choice = user_input.nextLine();

                                        if (best_choice.compareTo("Y") == 0 || best_choice.compareTo("y") == 0)
                                        {
                                            Reserve(A_choice, Integer.toString(best_seat.getPayload().getRow()), Character.toString(best_seat.getPayload().getSeat()), adult_tickets, child_tickets, senior_tickets);
                                            System.out.println("The best seats have been reserved.");

                                            for (int x = 0; x < reservation_Count; x++)
                                            {
                                                if (x + 1 == reservation_Count)
                                                    Order += Integer.toString(best_seat.getPayload().getRow()) + (char)(best_seat.getPayload().getSeat() + x);
                                                else
                                                    Order += Integer.toString(best_seat.getPayload().getRow()) + (char)(best_seat.getPayload().getSeat() + x) + ",";
                                            }

                                            //This modified order section that will add the order to the user in the hashmap
                                            Order += "\n";

                                            Order += adult_tickets + " adult, ";
                                            Order += child_tickets + " child, ";
                                            Order += senior_tickets + " senior";

                                            Data_Base.put(name_user, Data_Base.get(name_user) + Order);
                                        }
                                        else
                                            System.out.println("Returning to menu.");
                                    }
                                }
                            }

                            //This is if the user would like to see the orders in their account
                            else if (user_choice.compareTo("2") == 0)
                            {
                                String specified_order,
                                       whole_order,
                                       pars_order,
                                       pars_order2,
                                       pars_order3 = "";

                                int order_print = 0;

                                whole_order = Data_Base.get(name_user).substring(Data_Base.get(name_user).indexOf('\n') + 1);
                                pars_order = whole_order;

                                if (!Data_Base.get(name_user).contains("\n"))
                                    System.out.println("No orders");
                                else
                                {
                                    while(order_print == 0)
                                    {
                                        try
                                        {
                                            //This string manipulation parses the data part of the user to get
                                            // the specific order to then be outputted to be seen in the console
                                            pars_order3 = pars_order.substring(0, pars_order.indexOf('\n') + 1);
                                            pars_order = pars_order.substring(pars_order.indexOf('\n') + 1);
                                            pars_order2 = pars_order.substring(0,pars_order.indexOf('\n'));

                                            specified_order = pars_order3 + pars_order2;

                                            System.out.println("\n" + specified_order);

                                            pars_order = pars_order.substring(pars_order.indexOf('\n') + 1);
                                        }
                                        //This catch is used to output the last order because of the format
                                        // of how the orders were added through string manipulation
                                        catch(StringIndexOutOfBoundsException E)
                                        {
                                            specified_order = pars_order3 + pars_order;
                                            order_print++;
                                            System.out.println("\n" + specified_order);
                                        }
                                    }
                                }
                            }

                            //This is for if the user wants to update an order
                            else if (user_choice.compareTo("3") == 0)
                            {
                                String specified_order = null,
                                        whole_order,
                                        pars_order,
                                        pars_order2 = "",
                                        pars_order3 = "",
                                        pars_order3_first,
                                        pars_order3_second = "",
                                        pars_order3_copy,
                                        order_update;
                                int order_print = 0,
                                        order_num = 1,
                                        order_choice,
                                        adult,
                                        child,
                                        senior;

                                boolean order_valid = false;
                                whole_order = Data_Base.get(name_user).substring(Data_Base.get(name_user).indexOf('\n') + 1);
                                pars_order = whole_order;

                                //This test if the user has any orders on the account
                                if (whole_order.contains("\n"))
                                {
                                    while(order_print == 0)
                                    {
                                        //Since the account had orders it beings to print
                                        // them out so that the user can choose an order to update
                                        try
                                        {
                                            pars_order3 = pars_order.substring(0, pars_order.indexOf('\n') + 1);
                                            pars_order = pars_order.substring(pars_order.indexOf('\n') + 1);
                                            pars_order2 = pars_order.substring(0,pars_order.indexOf('\n'));

                                            specified_order = pars_order3 + pars_order2;

                                            System.out.println("\nOrder #" + order_num);
                                            System.out.println(specified_order);

                                            order_num++;
                                            pars_order = pars_order.substring(pars_order.indexOf('\n') + 1);
                                        }
                                        catch(StringIndexOutOfBoundsException E)
                                        {
                                            specified_order = pars_order3 + pars_order;
                                            order_print++;
                                            System.out.println("\nOrder #" + order_num);
                                            System.out.println(specified_order);
                                        }
                                    }

                                    //If while is used to validate that a chose order is valid
                                    while (!order_valid)
                                    {
                                        try
                                        {
                                            System.out.println("Enter the order to be modified");
                                            order_choice = Integer.parseInt(user_input.nextLine());

                                            if (order_choice > order_num)
                                                throw new Exception();

                                            //Through string manipulation, below this, manipulates
                                            // it to parse out and keep the order the user chose
                                            order_valid = true;
                                            pars_order = whole_order;
                                            for (int x = 0; x < order_choice; x++)
                                            {
                                                try
                                                {
                                                    pars_order3 = pars_order.substring(0, pars_order.indexOf('\n') + 1);
                                                    pars_order = pars_order.substring(pars_order.indexOf('\n') + 1);
                                                    pars_order2 = pars_order.substring(0,pars_order.indexOf('\n'));

                                                    specified_order = pars_order3 + pars_order2;

                                                    pars_order = pars_order.substring(pars_order.indexOf('\n') + 1);
                                                }
                                                catch(StringIndexOutOfBoundsException E)
                                                {
                                                    pars_order2 = pars_order;
                                                    specified_order = pars_order3 + pars_order2;
                                                }
                                            }

                                            //With the specified order, it parses the auditorium
                                            // that the order is connected to to modify it later
                                            int auditorium_num = Integer.parseInt(specified_order.substring(specified_order.indexOf(' ') + 1, specified_order.indexOf(',')));

                                            if (auditorium_num == 1)
                                                A_choice = A1;
                                            else if (auditorium_num == 2)
                                                A_choice = A2;
                                            else if (auditorium_num == 3)
                                                A_choice = A3;

                                            int exit_subloop = 0;
                                            while (exit_subloop != 1)
                                            {
                                                System.out.println("1. Add tickets to order");
                                                System.out.println("2. Delete tickets from order");
                                                System.out.println("3. Cancel Order");

                                                user_choice = user_input.nextLine();

                                                //With this selection, we go through the same reservation process from earlier
                                                // but do not provide the user with a best seat if the order is not available
                                                if (user_choice.compareTo("1") == 0)
                                                {
                                                    display_Chart(A_choice);
                                                    //Input validation for the row choice input
                                                    out = 0;
                                                    while (out != 1)
                                                    {
                                                        try
                                                        {
                                                            System.out.println("Enter row number of choice.");
                                                            Row_choice = user_input.nextLine();

                                                            if (Row_choice.length() > 1)
                                                                throw new IOException();

                                                            if (Integer.parseInt(Row_choice) <= 0 || Integer.parseInt(Row_choice) > A_choice.getRow_count())
                                                                throw new IOException();

                                                            out++;
                                                        }
                                                        catch(Exception ToyStory)
                                                        {
                                                            System.out.println("The input is invalid");
                                                        }
                                                    }

                                                    //Input validation for the seat choice input
                                                    out = 0;
                                                    while (out != 1)
                                                    {
                                                        try
                                                        {
                                                            System.out.println("Enter seat letter of choice.");
                                                            Seat_choice = user_input.nextLine();

                                                            if (Seat_choice.length() > 1)
                                                                throw new IOException();

                                                            if (!(Seat_choice.charAt(0) >= 'A') || !(Seat_choice.charAt(0) <= A_choice.getIndex(A_choice.getSeat_count(), 1).getPayload().getSeat()))
                                                                if(!(Seat_choice.charAt(0) >= 'a') || !(Seat_choice.charAt(0) <= Character.toLowerCase(A_choice.getIndex(A_choice.getSeat_count(), 1).getPayload().getSeat())))
                                                                    throw new IOException();

                                                            out++;
                                                        }
                                                        catch(Exception ToyStory)
                                                        {
                                                            System.out.println("The input is invalid");
                                                        }
                                                    }

                                                    //Input validation for the number of adult tickets
                                                    out = 0;
                                                    while (out != 1)
                                                    {
                                                        try
                                                        {
                                                            System.out.println("Enter the number of adult tickets(s) to be reserved.");
                                                            adult_tickets = user_input.nextInt();

                                                            if (adult_tickets < 0)
                                                                throw new IOException();

                                                            out++;
                                                        }
                                                        catch(Exception ToyStory)
                                                        {
                                                            System.out.println("The input is invalid");
                                                        }

                                                        user_input.nextLine();
                                                    }

                                                    //Input validation for the number of child tickets
                                                    out = 0;
                                                    while (out != 1)
                                                    {
                                                        try
                                                        {
                                                            System.out.println("Enter the number of child tickets(s) to be reserved.");
                                                            child_tickets = user_input.nextInt();

                                                            if (child_tickets < 0)
                                                                throw new IOException();

                                                            out++;
                                                        }
                                                        catch(Exception ToyStory)
                                                        {
                                                            System.out.println("The input is invalid");
                                                        }

                                                        user_input.nextLine();
                                                    }

                                                    //Input validation for the number of senior tickets
                                                    out = 0;
                                                    while (out != 1)
                                                    {
                                                        try
                                                        {
                                                            System.out.println("Enter the number of senior tickets(s) to be reserved.");
                                                            senior_tickets = user_input.nextInt();

                                                            if (senior_tickets < 0) {
                                                                throw new IOException();
                                                            }

                                                            out++;
                                                        }
                                                        catch(Exception ToyStory)
                                                        {
                                                            System.out.println("The input is invalid");
                                                        }

                                                        user_input.nextLine();
                                                    }

                                                    //This holds a local variable of the number of tickets needed for easier processing
                                                    reservation_Count = adult_tickets + child_tickets + senior_tickets;

                                                    //This boolean holds decision of if the reservation is available
                                                    available = Availability(A_choice, Row_choice, Seat_choice, reservation_Count);

                                                    //Just like in the previous reservation system, it is the
                                                    // same reservation system from project 2 but with the modification
                                                    // to keep track and update the order in the hashmap towards the end
                                                    if (available)
                                                    {
                                                        System.out.println("The requested seats have been reserved");
                                                        Reserve(A_choice, Row_choice, Seat_choice, adult_tickets, child_tickets, senior_tickets);

                                                        Order = "";
                                                        for (int x = 0; x < reservation_Count; x++)
                                                        {
                                                            if (x + 1 == reservation_Count)
                                                                Order += Row_choice + (char)(Seat_choice.toUpperCase().charAt(0) + x);
                                                            else
                                                                Order += Row_choice + (char)(Seat_choice.toUpperCase().charAt(0) + x) + ",";
                                                        }

                                                        //Below this creates the new order to be added into the hashmap
                                                        pars_order3 = pars_order3.substring(0, pars_order3.length() - 1);

                                                        pars_order3 += "," + Order + "\n";

                                                        pars_order3_copy = pars_order3;

                                                        pars_order3_first = pars_order3_copy.substring(0, pars_order3_copy.indexOf(',') + 1);
                                                        pars_order3_copy = pars_order3_copy.substring(pars_order3_copy.indexOf(' ') + 1);
                                                        pars_order3_copy = pars_order3_copy.substring(pars_order3_copy.indexOf(' ') + 1);

                                                        //This arraylist is used to hold all the
                                                        // seats that the order has reserved and
                                                        // then sort them as said in the documentation
                                                        ArrayList<String> order_sort = new ArrayList<>();
                                                        while (pars_order3_copy.contains(","))
                                                        {
                                                            order_sort.add(pars_order3_copy.substring(0,2));
                                                            pars_order3_copy = pars_order3_copy.substring(pars_order3_copy.indexOf(',') + 1);
                                                        }

                                                        order_sort.add(pars_order3_copy.substring(0,2));

                                                        Collections.sort(order_sort);

                                                        for (String i : order_sort)
                                                            pars_order3_second += i + ",";

                                                        pars_order3_second = pars_order3_second.substring(0,pars_order3_second.lastIndexOf(','));

                                                        pars_order3 = pars_order3_first + " " + pars_order3_second + "\n";

                                                        adult = Integer.parseInt(pars_order2.substring(0, pars_order2.indexOf(' '))) + adult_tickets;
                                                        pars_order2 = pars_order2.substring(pars_order2.indexOf(',') + 2);
                                                        child = Integer.parseInt(pars_order2.substring(0, pars_order2.indexOf(' '))) + child_tickets;
                                                        pars_order2 = pars_order2.substring(pars_order2.indexOf(',') + 2);
                                                        senior = Integer.parseInt(pars_order2.substring(0, pars_order2.indexOf(' '))) + senior_tickets;

                                                        pars_order2 = adult + " adult, " + child + " child, " + senior + " senior";

                                                        specified_order = pars_order3 + pars_order2;

                                                        order_update = Data_Base.get(name_user).substring(0, Data_Base.get(name_user).indexOf('\n'));

                                                        pars_order = whole_order;
                                                        for (int x = 0; x < order_num; x++)
                                                        {
                                                            try
                                                            {
                                                                pars_order3 = pars_order.substring(0, pars_order.indexOf('\n') + 1);
                                                                pars_order = pars_order.substring(pars_order.indexOf('\n') + 1);
                                                                pars_order2 = pars_order.substring(0,pars_order.indexOf('\n'));

                                                                if (x != order_choice - 1)
                                                                    order_update += "\n" + pars_order3 + pars_order2;
                                                                else
                                                                    order_update += "\n" + specified_order;


                                                                pars_order = pars_order.substring(pars_order.indexOf('\n') + 1);
                                                            }
                                                            catch(StringIndexOutOfBoundsException E)
                                                            {
                                                                pars_order2 = pars_order;
                                                                if (x != order_choice - 1)
                                                                    order_update += "\n" + pars_order3 + pars_order2;
                                                                else
                                                                    order_update += "\n" + specified_order;
                                                            }
                                                        }

                                                        Data_Base.put(name_user, order_update);
                                                        System.out.println("The seats have been added to the order.");
                                                    }
                                                    else
                                                        System.out.println("The seats were not added to the order.");

                                                    exit_subloop++;
                                                }

                                                //This is for if the user decides to delete
                                                // a seat from the the specified order
                                                else if (user_choice.compareTo("2") == 0)
                                                {
                                                    String row,
                                                           seat;

                                                    //This try catch is used to validate if the seat
                                                    // is a valid seat within the specified order
                                                    try
                                                    {
                                                        System.out.println("Enter the row");
                                                        row = user_input.nextLine();
                                                        System.out.println("Enter the seat");
                                                        seat = user_input.nextLine();

                                                        user_choice = row + seat;

                                                        if (!specified_order.contains(user_choice))
                                                            throw new Exception();
                                                    }
                                                    catch(Exception E)
                                                    {
                                                        System.out.println("The input is not valid");
                                                        break;
                                                    }

                                                    //This is used to delete the seat from the order
                                                    // with the replace function in the string class
                                                    if (pars_order3.contains(user_choice + ","))
                                                        pars_order3 = pars_order3.replace(user_choice + ",", "");
                                                    else
                                                        pars_order3 = pars_order3.replace("," + user_choice, "");

                                                    //This is then used to get the seat that was at the index of the
                                                    // Auditorium to then say that that seat is no longer in the order
                                                    Node<Seat> update = A_choice.getIndex(string_to_int(Character.toString(user_choice.charAt(1))), Integer.parseInt(Character.toString(user_choice.charAt(0))));

                                                    char seat_type = update.getPayload().getTicket_Type();
                                                    adult = Integer.parseInt(pars_order2.substring(0, pars_order2.indexOf(' ')));
                                                    pars_order2 = pars_order2.substring(pars_order2.indexOf(',') + 2);
                                                    child = Integer.parseInt(pars_order2.substring(0, pars_order2.indexOf(' ')));
                                                    pars_order2 = pars_order2.substring(pars_order2.indexOf(',') + 2);
                                                    senior = Integer.parseInt(pars_order2.substring(0, pars_order2.indexOf(' ')));

                                                    if (seat_type == 'A')
                                                        adult--;
                                                    else if (seat_type == 'C')
                                                        child--;
                                                    else if (seat_type == 'S')
                                                        senior--;

                                                    if (adult + child + senior != 0)
                                                    {
                                                        pars_order2 = adult + " adult, " + child + " child, " + senior + " senior";
                                                        specified_order = pars_order3 + pars_order2;
                                                    }
                                                    else
                                                        specified_order = "";

                                                    //This updates the seat in the linked list for the auditorium
                                                    update.setPayload(new Seat(update.getPayload().getRow(), update.getPayload().getSeat(), '.'));

                                                    order_update = Data_Base.get(name_user).substring(0, Data_Base.get(name_user).indexOf('\n'));

                                                    pars_order = whole_order;
                                                    for (int x = 0; x < order_num; x++)
                                                    {
                                                        try
                                                        {
                                                            pars_order3 = pars_order.substring(0, pars_order.indexOf('\n') + 1);
                                                            pars_order = pars_order.substring(pars_order.indexOf('\n') + 1);
                                                            pars_order2 = pars_order.substring(0,pars_order.indexOf('\n'));

                                                            if (x != order_choice - 1)
                                                                order_update += "\n" + pars_order3 + pars_order2;
                                                            else
                                                                order_update += "\n" + specified_order;


                                                            pars_order = pars_order.substring(pars_order.indexOf('\n') + 1);
                                                        }
                                                        catch(StringIndexOutOfBoundsException E)
                                                        {
                                                            pars_order2 = pars_order;
                                                            if (x != order_choice - 1)
                                                                order_update += "\n" + pars_order3 + pars_order2;
                                                            else
                                                                order_update += "\n" + specified_order;
                                                        }
                                                    }

                                                    if (order_update.charAt(order_update.length() - 1) == '\n')
                                                        order_update = order_update.substring(0, order_update.length() - 1);

                                                    //This updates the seat in the users account in the hashmap
                                                    // from the prior string manipulation that parses the order,
                                                    // makes the modification, then puts the modified order into the account
                                                    Data_Base.put(name_user, order_update);

                                                    exit_subloop++;

                                                    if (adult + child + senior != 0)
                                                        System.out.println("The seat has been deleted from the order.");
                                                    else
                                                        System.out.println("The seat and order have been deleted from the account");
                                                }

                                                //This is if the user decides to Cancel an order
                                                else if (user_choice.compareTo("3") == 0)
                                                {
                                                    String seat_index;
                                                    int number_seats = 0;
                                                    order_update = Data_Base.get(name_user);

                                                    //Since the order was found prior, we just replace
                                                    // the order with nothing from the whole account
                                                    order_update = order_update.replace("\n" + specified_order, "");

                                                    pars_order3 = pars_order3.substring(pars_order3.indexOf(' ') + 1);
                                                    pars_order3 = pars_order3.substring(pars_order3.indexOf(' ') + 1);

                                                    //This is used to get the number of seats in the order to then be looped through later
                                                    for (int x = 0; x < pars_order3.length(); x++)
                                                    {
                                                        if (pars_order3.charAt(x) == ',')
                                                            number_seats++;
                                                    }
                                                    number_seats++;

                                                    //This then updates all the seats in the Auditorium that were included in this order
                                                    for (int x = 0; x < number_seats; x++)
                                                    {
                                                        try
                                                        {
                                                            seat_index = pars_order3.substring(0,pars_order3.indexOf(','));

                                                            Node<Seat> update = A_choice.getIndex(string_to_int(Character.toString(seat_index.charAt(1))), Integer.parseInt(Character.toString(seat_index.charAt(0))));

                                                            update.setPayload(new Seat(update.getPayload().getRow(), update.getPayload().getSeat(), '.'));

                                                            pars_order3 = pars_order3.substring(pars_order3.indexOf(',') + 1);
                                                        }
                                                        catch(Exception E)
                                                        {
                                                            seat_index = pars_order3.substring(0, pars_order3.indexOf('\n'));

                                                            Node<Seat> update = A_choice.getIndex(string_to_int(Character.toString(seat_index.charAt(1))), Integer.parseInt(Character.toString(seat_index.charAt(0))));

                                                            update.setPayload(new Seat(update.getPayload().getRow(), update.getPayload().getSeat(), '.'));
                                                        }
                                                    }

                                                    //This updates the orders in the hashmap
                                                    Data_Base.put(name_user, order_update);
                                                    exit_subloop++;

                                                    System.out.println("The order has been canceled");
                                                }
                                                else
                                                    System.out.println("Menu input is not valid");
                                            }
                                        }
                                        catch(Exception E)
                                        {
                                            System.out.println("The order number input was invalid");
                                        }
                                    }
                                }
                                else
                                {
                                    System.out.println("No orders are available to modify");
                                }
                            }

                            //This is for if the user decides to print a receipt to the console
                            else if (user_choice.compareTo("4") == 0)
                            {
                                int order_print = 0;

                                double total_specified,
                                        total = 0;

                                String specified_order,
                                        pars_order,
                                        pars_order2,
                                        pars_order3 = "",
                                        pars_total;

                                pars_order = Data_Base.get(name_user).substring(Data_Base.get(name_user).indexOf('\n') + 1);

                                if (pars_order.contains("\n"))
                                {
                                    while(order_print == 0)
                                    {
                                        total_specified = 0;

                                        //This try catch takes care of the different case the is the last order in the file
                                        try
                                        {
                                            //This parses the order to get the specified order then prints it out
                                            pars_order3 = pars_order.substring(0, pars_order.indexOf('\n') + 1);
                                            pars_order = pars_order.substring(pars_order.indexOf('\n') + 1);
                                            pars_order2 = pars_order.substring(0,pars_order.indexOf('\n'));

                                            specified_order = pars_order3 + pars_order2;

                                            System.out.println(specified_order);

                                            pars_total = pars_order2;

                                            //This part then creates a running total of all the seats in the order based on what type of ticket it is and prints the total of the order
                                            total_specified += (double)(Integer.parseInt(pars_total.substring(0, pars_total.indexOf(' ')))) * ADULT_P;
                                            pars_total = pars_total.substring(pars_total.indexOf(',') + 2);
                                            total_specified += (double)(Integer.parseInt(pars_total.substring(0, pars_total.indexOf(' ')))) * CHILD_P;
                                            pars_total = pars_total.substring(pars_total.indexOf(',') + 2);
                                            total_specified += (double)(Integer.parseInt(pars_total.substring(0, pars_total.indexOf(' ')))) * SENIOR_P;

                                            System.out.println("Order Total: $" + String.format("%.2f", total_specified) + "\n");

                                            total += total_specified;

                                            pars_order = pars_order.substring(pars_order.indexOf('\n') + 1);
                                        }

                                        //This catches the last order in the hashmap but does the
                                        // same thing as prior except that is shows the last order
                                        // and then shows the customer total of all their orders combined
                                        catch(StringIndexOutOfBoundsException E)
                                        {
                                            specified_order = pars_order3 + pars_order;

                                            System.out.println(specified_order);

                                            pars_total = pars_order;

                                            total_specified += (double)(Integer.parseInt(pars_total.substring(0, pars_total.indexOf(' ')))) * ADULT_P;
                                            pars_total = pars_total.substring(pars_total.indexOf(',') + 2);
                                            total_specified += (double)(Integer.parseInt(pars_total.substring(0, pars_total.indexOf(' ')))) * CHILD_P;
                                            pars_total = pars_total.substring(pars_total.indexOf(',') + 2);
                                            total_specified += (double)(Integer.parseInt(pars_total.substring(0, pars_total.indexOf(' ')))) * SENIOR_P;

                                            System.out.println("Order Total: $" + String.format("%.2f", total_specified) + "\n");

                                            total += total_specified;

                                            System.out.println("Customer Total: $" + String.format("%.2f", total));

                                            order_print++;
                                        }
                                    }
                                }

                                //This takes care of if the user does not have any orders in the account
                                else
                                {
                                    System.out.println("No orders available");
                                    System.out.println("Customer Total: $" + String.format("%.2f", total));
                                }
                            }

                            //This is used to log the user out of the of the UI
                            else if (user_choice.compareTo("5") == 0)
                            {
                                System.out.println("Logging out of user: " + name_user);
                                System.out.println("Have a nice day!");

                                user_exit = true;
                            }
                            else
                                System.out.println("Menu input invalid");

                            System.out.println();
                        }
                    }
                }
            }

            //This else takes care of if the username is not in the data-base
            else
                System.out.println("The username is not in our data base");
        }
    }

    //This looks like the last project I'll ever do for you Mr.Smith, It's been a good run,
    // and it looks like were in the end game now (avengers reference) I hope you have a
    // good Break and I hope you stay safe and healthy out there

    //This function takes an Auditorium and displays it accordingly exactly what is inside it and proper format
    public static void display_Chart(Auditorium grid)
    {
        //Below this formats the first line of the console to show the seat letter
        System.out.print("  ");
        for (int x = 0; x < grid.getSeat_count(); x++)
            System.out.print((char)('A' + x));

        System.out.println();

        //This prints the row number and occupied and open seats
        for (int y = 1; y <= grid.getRow_count(); y++)
        {
            System.out.print(y + " ");
            for (int x = 1; x <= grid.getSeat_count(); x++)
            {
                if (grid.getIndex(x,y).getPayload().getTicket_Type() == 'A' || grid.getIndex(x,y).getPayload().getTicket_Type() == 'C' || grid.getIndex(x,y).getPayload().getTicket_Type() == 'S')
                    System.out.print('#');
                else
                    System.out.print(grid.getIndex(x,y).getPayload().getTicket_Type());
            }

            System.out.println();
        }
    }

    //This function takes the Auditorium variable, and starting seat location and checks the following seats after it to return if the seat is available
    public static boolean Availability(Auditorium grid, String row, String seat, int seat_total)
    {
        char occupancy;

        try
        {
            for (int x = 0; x < seat_total; x++)
            {
                occupancy = grid.getIndex(string_to_int(seat) + x, Integer.parseInt(row)).getPayload().getTicket_Type();

                if (occupancy == 'A' || occupancy == 'C' || occupancy == 'S')
                {
                    return false;
                }
            }
        }
        catch (Exception E)
        {
            return false;
        }

        return true;
    }

    //This function takes the Auditorium variable, starting seat location
    // and the number of ticket types to be reserved and reserves them
    public static void Reserve(Auditorium grid, String row, String seat, int a_ticket, int c_ticket, int s_ticket)
    {
        int count = 0,
                move = 0;

        //Reserves adult tickets accordingly
        while (count < a_ticket)
        {
            grid.getIndex(string_to_int(seat) + move, Integer.parseInt(row)).getPayload().setTicket_Type('A');
            count++;
            move++;
        }

        //Reserves children tickets accordingly
        count = 0;
        while (count < c_ticket)
        {
            grid.getIndex(string_to_int(seat) + move, Integer.parseInt(row)).getPayload().setTicket_Type('C');
            count++;
            move++;
        }

        //Reserves senior tickets accordingly
        count = 0;
        while (count < s_ticket)
        {
            grid.getIndex(string_to_int(seat) + move, Integer.parseInt(row)).getPayload().setTicket_Type('S');
            count++;
            move++;
        }
    }

    //This function is used to find the best available seat in the auditorium based
    // that the dead center of the auditorium being the best location of the theatre
    public static Node<Seat> Best_available(Auditorium grid, int total_seats)
    {
        double  middle_row = grid.getRow_count() / 2.0 + 0.5,
                middle_cell = grid.getSeat_count() / 2.0 + 0.5,
                cell_check = 0,
                shortest_distance = 0,
                distance = 0;

        int row = 1,
                cell = 0,
                shortest_row = 1,
                shortest_cell = 1,
                distance_count = 0;

        boolean available = false;

        //This outer loop takes care of the number of rows in the  linked list
        while (row != grid.getRow_count() + 1)
        {
            //This next loop takes care of each seat that is total - 1 from the edge of the row
            for (cell = 1; cell <= (grid.getSeat_count() - total_seats + 1); cell++)
            {

                available = Availability(grid, Integer.toString(row), int_to_string(cell), total_seats);

                //If the selection is available, then the math is done to determine
                // if the seat section is a better candidate for the best seat
                if (available)
                {
                    if (total_seats == 1)
                        cell_check = cell;
                    else
                        cell_check = cell + (total_seats / 2.0) - 0.5;

                    //This checks if it was the first time that the seat selection was valid
                    // and seats the parameters to check if there is another better seat
                    if (distance_count == 0)
                    {
                        shortest_distance = Math.sqrt(Math.pow((cell_check - middle_cell), 2) + Math.pow(((row) - middle_row), 2));
                        distance = shortest_distance;
                        shortest_row = row;
                        shortest_cell = cell;
                        distance_count++;
                    }

                    //This is triggered when another selection was found prior to continue the mathematical comparison
                    else
                        distance = Math.sqrt(Math.pow((cell_check - middle_cell), 2) + Math.pow((row - middle_row), 2));

                    //This is for if the distance of the selection is shorter to update the new shortest seat from the middle
                    if (distance < shortest_distance)
                    {
                        shortest_distance = distance;
                        shortest_row = row;
                        shortest_cell = cell;
                    }
                    //This is triggered when the distance is the same but if its closer to the center row then it updates the shortest row and cell number
                    else if (distance == shortest_distance && Math.abs(row - middle_row) < Math.abs(shortest_row - middle_row))
                    {
                        shortest_row = row;
                        shortest_cell = cell;
                    }
                    //This is triggered when the distance is the same, the number rows away from the middle are the same then the first best seat is saved and returned
                    else if (distance == shortest_distance && Math.abs(row - middle_row) == Math.abs(shortest_row - middle_row))
                    {
                        if (row < shortest_row)
                            break;
                    }
                }
            }

            row++;
        }

        if (distance_count == 0)
            return null;
        else
            return grid.getIndex(shortest_cell, shortest_row);
    }

    //This is used to convert a given string char into a designated integer
    public static int string_to_int (String input)
    {
        switch (input.charAt(0))
        {
            case 'a':
            case 'A': return 1;
            case 'b':
            case 'B': return 2;
            case 'c':
            case 'C': return 3;
            case 'd':
            case 'D': return 4;
            case 'e':
            case 'E': return 5;
            case 'f':
            case 'F': return 6;
            case 'g':
            case 'G': return 7;
            case 'h':
            case 'H': return 8;
            case 'i':
            case 'I': return 9;
            case 'j':
            case 'J': return 10;
            case 'k':
            case 'K': return 11;
            case 'l':
            case 'L': return 12;
            case 'm':
            case 'M': return 13;
            case 'n':
            case 'N': return 14;
            case 'o':
            case 'O': return 15;
            case 'p':
            case 'P': return 16;
            case 'q':
            case 'Q': return 17;
            case 'r':
            case 'R': return 18;
            case 's':
            case 'S': return 19;
            case 't':
            case 'T': return 20;
            case 'u':
            case 'U': return 21;
            case 'v':
            case 'V': return 22;
            case 'w':
            case 'W': return 23;
            case 'x':
            case 'X': return 24;
            case 'y':
            case 'Y': return 25;
            case 'z':
            case 'Z': return 26;
        }

        return -1;
    }

    //This is used to convert a given integer into a string that only contains the char of the numerical representation
    public static String int_to_string(int input)
    {
        switch (input)
        {
            case 1: return "A";
            case 2: return "B";
            case 3: return "C";
            case 4: return "D";
            case 5: return "E";
            case 6: return "F";
            case 7: return "G";
            case 8: return "H";
            case 9: return "I";
            case 10: return "J";
            case 11: return "K";
            case 12: return "L";
            case 13: return "M";
            case 14: return "N";
            case 15: return "O";
            case 16: return "P";
            case 17: return "Q";
            case 18: return "R";
            case 19: return "S";
            case 20: return "T";
            case 21: return "U";
            case 22: return "V";
            case 23: return "W";
            case 24: return "X";
            case 25: return "Y";
            case 26: return "Z";
            default: return null;
        }
    }
}
