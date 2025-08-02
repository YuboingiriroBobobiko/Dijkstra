/**
 * This class is responsible for reading and writing graphs to files
 *
 * Dylan fage-Brown
 * 2025/07/24
 */


import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class FileInterface
{
    public static Graph readGraph(String filename, GUI gui) {
        File file = new File(filename);
        Scanner fileReader;
        try {
            fileReader = new Scanner(file);
        } catch (IOException e) {
            gui.showErrorDialog("Could not open the file"); // Need a better way to handle this
            return null;
        }
        
        Graph graph = new Graph();
        
        if (!fileReader.hasNextLine()) {
            gui.showErrorDialog("Expected number of nodes, got end of file");
            return null;
        }
        int numNodes = Integer.parseInt(fileReader.nextLine());
        for (int i = 0; i < numNodes; i++) {
            if (!fileReader.hasNextLine()) {
                gui.showErrorDialog("Expected node, got end of file");
                return null;
            }   
            String line = fileReader.nextLine();
            String[] fields = line.split(",");
            
            if (fields.length != 3) {
                gui.showErrorDialog("Incorrect number of fields (at node " + i + ")");
                return null;
            }
            
            String name = fields[0];
            int posX = Integer.parseInt(fields[1]);
            int posY = Integer.parseInt(fields[2]);
            
            Node node = new Node(name, posX, posY);
            
            graph.addNode(node);
        }
        
        if (!fileReader.hasNextLine()) {
            gui.showErrorDialog("Expected number of connections, got end of file");
            return null;
        }
        int numConnections = Integer.parseInt(fileReader.nextLine());
        for (int i = 0; i < numConnections; i++) {
            if (!fileReader.hasNextLine()) {
                gui.showErrorDialog("Expected connection, got end of file");
                return null;
            }   
            String line = fileReader.nextLine();
            String[] fields = line.split(",");
            
            if (fields.length != 3) {
                gui.showErrorDialog("Incorrect number of fields (at connection " + i + ")");
                return null;
            }
            
            String nodeNameA = fields[0];
            String nodeNameB = fields[1];
            double length = Float.parseFloat(fields[2]);
            
            Node nodeA = graph.getNodeByName(nodeNameA);
            if (nodeA == null) {
                gui.showErrorDialog("Nonexistent node A (at connection " + i + ")");
            }
            Node nodeB = graph.getNodeByName(nodeNameB);
            if (nodeB == null) {
                gui.showErrorDialog("Nonexistent node B (at connection " + i + ")");
            }
            
            nodeA.addConnection(new Connection(nodeA, nodeB, length));
            nodeB.addConnection(new Connection(nodeB, nodeA, length));
        }
        
        return graph;
    }
    
    
    public static void writeGraph(String filename, Graph graph, GUI gui) {
        File file = new File(filename);
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file);
            
            ArrayList<Connection> connections = new ArrayList<>();
            
            // Write the nodes themselves
            int numNodes = graph.getNodes().size();
            fileWriter.write(Integer.toString(numNodes));
            for (int i = 0; i < numNodes; i++) {
                Node currentNode = graph.getNodeByIndex(i);
                fileWriter.write("\n" + currentNode.stringify());
                
                // Also add the connections to a list
                for (Connection connection : currentNode.getConnections()) {
                    // Only add it if it's not a duplicate of an existing connection
                    boolean isUnique = true;
                    for (Connection testConnection : connections) {
                        if (testConnection.getSource() == connection.getDest()
                         && testConnection.getDest() == connection.getSource()) {
                            isUnique = false;
                            break;
                        }
                    }
                    
                    if (isUnique) {
                        connections.add(connection);
                    }
                }
            }
            
            // Then write the connections
            fileWriter.write("\n" + connections.size());
            for (int i = 0; i < connections.size(); i++) {
                fileWriter.write("\n" + connections.get(i).stringify());
            }
            
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            gui.showErrorDialog("Could not save the file");
            return;
        }
    }
}