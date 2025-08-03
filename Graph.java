/**
 * Holds a list of Nodes and can search through them.
 *
 * Dylan fage-Brown
 * 2025/08/03
 */


import java.util.ArrayList;


public class Graph
{
    private ArrayList<Node> nodes;
    
    public Graph() {
        nodes = new ArrayList<>();
    }
    
    public void addNode(Node node) {
        nodes.add(node);
    }
    
    public ArrayList<Node> getNodes() {
        return nodes;
    }
    public Node getNodeByName(String name) {
        for (Node node : nodes) {
            if (node.name.toLowerCase().equals(name.toLowerCase())) {
                return node;
            }
        }
        
        return null;
    }
    public Node getNodeByIndex(int index) {
        return nodes.get(index);
    }
    public void removeNode(Node node) {
        nodes.remove(node);
    }
}