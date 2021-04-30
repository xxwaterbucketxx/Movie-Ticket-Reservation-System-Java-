//Name: Victor Vu
//netID: vhv180000

import java.io.*;
import java.util.*;

public class Auditorium
{
    private Node<Seat> First;
    private int row_count;
    private int seat_count;

    //Constructor
    public Auditorium(String file)
    {
        String line;
        Scanner input_f;
        row_count = 0;
        seat_count = 0;
        int x = 1;
        int y = 1;
        char type;

        Node<Seat> helper_1;
        Node<Seat> helper_2;

        try
        {
            input_f = new Scanner(new File(file));
        }
        catch (FileNotFoundException E)
        {
            System.out.println("The file could not be found.");
            First = null;
            return;
        }

        while(input_f.hasNext())
        {
            line = input_f.nextLine();
            seat_count = line.length();

            for(int loop = 1; loop <= seat_count; loop++)
            {
                type = line.charAt(x - 1);

                Seat newSeat = new Seat(y,seatNum_to_Char(x), type);

                Node<Seat> newNode = new Node<>(newSeat);

                if (x == 1 && y == 1)
                {
                    First = newNode;
                }
                else if (x > 1 && y == 1)
                {
                    helper_1 = First;

                    for (int move_right = 1; move_right < x - 1; move_right++)
                        helper_1 = helper_1.getRight();


                    helper_1.setRight(newNode);
                    newNode.setLeft(helper_1);
                }
                else if (x == 1 && y == 2)
                {
                    First.setDown(newNode);
                    newNode.setUp(First);
                }
                else if (x == 1)
                {
                    helper_1 = First;

                    for (int move_down = 1; move_down < y - 1; move_down++)
                    {
                        helper_1 = helper_1.getDown();
                    }

                    helper_1.setDown(newNode);
                    newNode.setUp(helper_1);
                }
                else
                {
                    helper_1 = helper_2 = First;

                    for (int move_down = 1; move_down < y; move_down++)
                        helper_1 = helper_1.getDown();
                    for (int move_right = 1; move_right < x - 1; move_right++)
                        helper_1 = helper_1.getRight();


                    for (int move_down = 1; move_down < y - 1; move_down++)
                        helper_2 = helper_2.getDown();
                    for (int move_right = 1; move_right < x; move_right++)
                        helper_2 = helper_2.getRight();


                    helper_2.setDown(newNode);
                    newNode.setUp(helper_2);
                    helper_1.setRight(newNode);
                    newNode.setLeft(helper_1);
                }

                x++;

                if (x == seat_count + 1)
                {
                    row_count = y;
                    y++;
                    x = 1;
                }
            }
        }
    }

    //Mutators
    public void setGrid(Node<Seat> head)   {First = head;}

    //Accessors
    public int getRow_count()   {return row_count;}
    public int getSeat_count()  {return seat_count;}

    //Methods

    public char seatNum_to_Char(int number)
    {
        switch (number)
        {
            case 1: return 'A';
            case 2: return 'B';
            case 3: return 'C';
            case 4: return 'D';
            case 5: return 'E';
            case 6: return 'F';
            case 7: return 'G';
            case 8: return 'H';
            case 9: return 'I';
            case 10: return 'J';
            case 11: return 'K';
            case 12: return 'L';
            case 13: return 'M';
            case 14: return 'N';
            case 15: return 'O';
            case 16: return 'P';
            case 17: return 'Q';
            case 18: return 'R';
            case 19: return 'S';
            case 20: return 'T';
            case 21: return 'U';
            case 22: return 'V';
            case 23: return 'W';
            case 24: return 'X';
            case 25: return 'Y';
            case 26: return 'Z';

        }

        return '0';
    }

    //This method is used to return the node of the designated location within the 2d grid
    public Node<Seat> getIndex(int x, int y)
    {
        Node<Seat> indexer = First;

        for (int move_down = 1; move_down < y; move_down++)
            indexer = indexer.getDown();
        for (int move_right = 1; move_right < x; move_right++)
            indexer = indexer.getRight();

        return indexer;
    }
}
