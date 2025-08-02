/**
 * Very simple class that contains a connection bewteen Nodes and the distance.
 *
 * Dylan fage-Brown
 * 2025/07/20
 */



public class Connection
{
    public double distance;
    private Node source;
    private Node dest;
    
    public Connection(Node source, Node dest, double distance) {
        this.source = source;
        this.dest = dest;
        this.distance = distance;
    }
    
    public Node getSource() {
        return source;
    }
    public Node getDest() {
        return dest;
    }
    
    public String stringify() {
        return source.name + "," + dest.name + "," + distance;
    }
}