//Name: Victor Vu
//netID: vhv180000

public class Node <G>
{
    private Node<G> Up;
    private Node<G> Down;
    private Node<G> Left;
    private Node<G> Right;
    private G       Payload;

    //Constructor
    public Node()
    {
        Up = null;
        Down = null;
        Left = null;
        Right = null;
        Payload = null;
    }

    public Node(G payload)
    {
        Up = null;
        Down = null;
        Left = null;
        Right = null;
        Payload = payload;
    }

    //Mutators
    public void setUp(Node<G> up)       {Up = up;}
    public void setDown(Node<G> down)   {Down = down;}
    public void setLeft(Node<G> left)   {Left = left;}
    public void setRight(Node<G> right) {Right = right;}
    public void setPayload(G payload)   {Payload = payload;}

    //Accessors
    public Node<G> getUp()      {return Up;}
    public Node<G> getDown()    {return Down;}
    public Node<G> getLeft()    {return Left;}
    public Node<G> getRight()   {return Right;}
    public G getPayload()       {return Payload;}

}