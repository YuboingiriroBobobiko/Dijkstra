/**
 * A node in the graph.
 *
 * Dylan fage-Brown
 * 2025/08/03
 */


import java.util.ArrayList;


public class Node
{
    public String name;
    public int positionX;
    public int positionY;
    private ArrayList<Connection> connections;
    
    public boolean isStart;
    public boolean isEnd;
    public boolean visited;
    public int highlightedIdx;
    public double distance;
    public void resetDijkstraStats() {
        isStart = false;
        isEnd = false;
        visited = false;
        highlightedIdx = -1;
        distance = Double.POSITIVE_INFINITY;
        for (Connection conn : connections) {
            conn.highlighted = false;
        }
    }
    
    public Node(String nodeName, int posX, int posY) {
        name = nodeName;
        positionX = posX;
        positionY = posY;
        connections = new ArrayList<>();
        resetDijkstraStats();
    }
    
    public ArrayList<Connection> getConnections() {
        return connections;
    }
    public void addConnection(Connection newConnection) {
        if (connections.contains(newConnection)) { // Don't want duplicates
            return;
        }
        
        connections.add(newConnection);
    }
    public void removeConnection(Connection connection) {
        connections.remove(connection);
    }
    
    public String stringify() {
        return name + "," + positionX + "," + positionY;
    }
}