//Name: Victor Vu
//netID: vhv180000

public class Seat
{
    private int     Row;
    private char    Seat;
    private char    Ticket_Type;

    //Constructor
    public Seat()
    {
        Row = 0;
        Seat = '0';
        Ticket_Type = '0';
    }

    public Seat(int row, char seat, char ticket_type)
    {
        Row = row;
        Seat = seat;
        Ticket_Type = ticket_type;
    }


    //Mutators
    public void setRow(int row)                     {Row = row;}
    public void setSeat(char seat)                  {Seat = seat;}
    public void setTicket_Type(char ticket_type)    {Ticket_Type = ticket_type;}

    //Accessors
    public int getRow()             {return Row;}
    public char getSeat()           {return Seat;}
    public char getTicket_Type()    {return Ticket_Type;}
}