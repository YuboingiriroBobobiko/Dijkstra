/**
 * This class implements Dijkstra's algorithm, working out the shortest path to the ending node.
 *
 * Dylan fage-Brown
 * 2025/08/03
 */


public class Dijkstra
{
    private static boolean running = false;
    public static boolean isRunning() {
        return running;
    }
    public enum State {FIND_CLOSEST, FOUND_CLOSEST, UPDATE_NEIGHBOURS, UPDATED_NEIGHBOURS, FIND_PATH, FOUND_PATH}
    public static State currState;
    public static int currIdx;
    
    private static Node startNode;
    private static Node endNode;
    private static Node closestNode;
    private static Node currPathNode;
    private static Node selectNode(Graph graph, GUI gui, String text) {
        String name = gui.showInputDialog(text);
        if (name == null) {return null;}
        Node node = graph.getNodeByName(name);
        if (node == null) {
            gui.showErrorDialog("No node by that name exists");
            return null;
        }
        return node;
    }
    
    /*
     * Controls the entire algorithm. At each step, it runs whatever state is dictated by currState.
     */
    public static void step(Graph graph, GUI gui) {
        // If we're not running, then the this is the first step and so we ask for the start node.
        if (!running) {
            startNode = selectNode(graph, gui, "Enter the name of the starting node:");
            if (startNode == null) {return;}
            
            endNode = selectNode(graph, gui, "Enter the name of the ending node:");
            if (endNode == null) {return;}
            
            if (startNode == endNode) {
                gui.showErrorDialog("Start and end nodes cannot be the same");
                return;
            }
            
            startNode.isStart = true;
            startNode.distance = 0;
            endNode.isEnd = true;
            
            running = true;
            currState = State.FIND_CLOSEST;
            currIdx = 0;
            return;
        }
        
        currIdx += 1;
        
        if (currState == State.FIND_CLOSEST) {
            double closestDistance = Double.POSITIVE_INFINITY;
            closestNode = null;
            for (Node node : graph.getNodes()) {
                if (node.distance < closestDistance && !node.visited) {
                    closestDistance = node.distance;
                    closestNode = node;
                }
            }
            if (closestNode == null) { // Done.
                gui.showMessageDialog("No path found. Press ctrl+R to reset.", "Done");
                return;
            }
            
            closestNode.highlightedIdx = currIdx;
            currState = State.FOUND_CLOSEST;
            
            // If we find the end node then it must be the shortest distance
            if (closestNode == endNode) {
                closestNode.highlightedIdx = Integer.MAX_VALUE; // Make the highlighting never fade
                currPathNode = endNode;
                currState = State.FIND_PATH;
            }
            
            return;
        }
        
        if (currState == State.FOUND_CLOSEST) {
            for (Connection connection : closestNode.getConnections()) {
                Node neighbourNode = connection.getDest();
                if (!neighbourNode.visited) {
                    neighbourNode.highlightedIdx = currIdx;
                }
            }
            
            currState = State.UPDATE_NEIGHBOURS;
            return;
        }
        
        if (currState == State.UPDATE_NEIGHBOURS) {
            closestNode.visited = true;
            
            for (Connection connection : closestNode.getConnections()) {
                Node neighbourNode = connection.getDest();
                if (!neighbourNode.visited) {
                    neighbourNode.highlightedIdx = currIdx;
                    double distanceFromHere = closestNode.distance + connection.distance;
                    if (distanceFromHere < neighbourNode.distance) {
                        neighbourNode.distance = distanceFromHere;
                        neighbourNode.prevPathConn = connection.getOpposite();
                    }
                }
            }
            
            currState = State.UPDATED_NEIGHBOURS;
            return;
        }
        
        if (currState == State.UPDATED_NEIGHBOURS) {
            currState = State.FIND_CLOSEST;
            return;
        }
        
        if (currState == State.FIND_PATH) {
            Connection nextConn = currPathNode.prevPathConn;
            if (nextConn == null) {
                gui.showErrorDialog("Next node in path is null!");
                return;
            }
            
            nextConn.highlighted = true;
            nextConn.getOpposite().highlighted = true;
            
            currPathNode = nextConn.getDest();
            currPathNode.highlightedIdx = Integer.MAX_VALUE;;
            if (currPathNode == startNode) {
                currState = State.FOUND_PATH;
            }
            return;
        }
        
        if (currState == State.FOUND_PATH) {
            // Do nothing
            return;
        }
    }
    
    public static void reset(Graph graph) {
        running = false;
        
        for (Node node : graph.getNodes()) {
            node.resetDijkstraStats();
        }
    }
}