/**
 * Very simple class that contains a connection bewteen Nodes and the distance.
 *
 * Dylan fage-Brown
 * 2025/07/24
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
    
    public Connection getOpposite() {
        for (Connection conn : dest.getConnections()) {
            if (conn.getDest() == source) {
                return conn;
            }
        }
        
        return null;
    }
    
    public String stringify() {
        return source.name + "," + dest.name + "," + distance;
    }
}