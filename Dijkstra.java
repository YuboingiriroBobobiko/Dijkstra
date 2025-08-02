/**
 * This class implements Dijkstra's algorithm, working out the shortest path to every node.
 *
 * Dylan fage-Brown
 * 2025/08/02
 */





public class Dijkstra
{
    private static boolean running = false;
    public static boolean isRunning() {
        return running;
    }
    
    private static Node startNode;
    
    public static void step(Graph graph, GUI gui) {
        // If we're not running, then the this is the first step and so we ask for the start node.
        if (!running) {
            String name = gui.showInputDialog("Enter the name of the starting node:");
            if (name == null) {return;}
            startNode = graph.getNodeByName(name);
            if (startNode == null) {
                gui.showErrorDialog("No node by that name exists");
                return;
            }
            startNode.isStart = true;
            startNode.distance = 0;
            
            running = true;
            return;
        }
        
        
        double closestDistance = Double.POSITIVE_INFINITY;
        Node closestNode = null;
        for (Node node : graph.getNodes()) {
            if (node.distance < closestDistance && !node.visited) {
                closestDistance = node.distance;
                closestNode = node;
            }
        }
        if (closestNode == null) {return;} // Done.
        
        closestNode.visited = true;
        
        for (Connection connection : closestNode.getConnections()) {
            Node neighbourNode = connection.getDest();
            if (!neighbourNode.visited) {
                double distanceFromHere = closestNode.distance + connection.distance;
                if (distanceFromHere < neighbourNode.distance) {
                    neighbourNode.distance = distanceFromHere;
                }
            }
        }
    }
    
    public static void reset(Graph graph) {
        running = false;
        
        for (Node node : graph.getNodes()) {
            node.resetDijkstraStats();
        }
    }
}