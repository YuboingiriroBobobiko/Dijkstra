/**
 * Very simple class that contains a connection bewteen Nodes and the distance.
 *
 * Dylan fage-Brown
 * 2025/08/03
 */



public class Connection
{
    public double distance;
    private Node source;
    private Node dest;
    
    public boolean highlighted;
    
    public Connection(Node source, Node dest, double distance) {
        this.source = source;
        this.dest = dest;
        this.distance = distance;
        this.highlighted = false;
    }
    
    public Node getSource() {
        return source;
    }
    public Node getDest() {
        return dest;
    }
    
    // There are actually 2 Connections between nodes, one for each direction. This means they can get out of sync
    // This functio makes it easy to update both directions at once.
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