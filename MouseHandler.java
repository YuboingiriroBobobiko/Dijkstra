/**
 * This class handles the mouse movement events and editing the graph.
 *
 * Dylan fage-Brown
 * 2025/07/24
 */


import java.awt.event.*;


public class MouseHandler implements MouseListener, MouseMotionListener
{
    private Graph graph;
    private GUI gui;
    
    private static final int NODE_RADIUS = 75;
    private static final int CONN_RADIUS = 50;
    
    private boolean isDragging = false;
    private Node selectedNode;
    private int dragOffsetX;
    private int dragOffsetY;
    
    public MouseHandler(GUI gui) {
        this.gui = gui;
        
        gui.addMouseListener(this);
        gui.addMouseMotionListener(this);
    }
    
    public void setGraph(Graph graph) {
        this.graph = graph;
    }
    
    private Node getNodeAtPosition(int x, int y) {
        for (Node node : graph.getNodes()) {
            int relX = node.positionX - x;
            int relY = node.positionY - y;
            
            if (relX * relX + relY * relY <= NODE_RADIUS * NODE_RADIUS) {
                return node;
            }
        }
        
        return null;
    }
    private Connection getConnectionAtPosition(int x, int y) {
        for (Node node : graph.getNodes()) {
            for (Connection conn : node.getConnections()) {
                Node nodeA = conn.getSource();
                Node nodeB = conn.getDest();
                
                // Halfway between both nodes
                int relX = (nodeA.positionX + nodeB.positionX) / 2 - x;
                int relY = (nodeA.positionY + nodeB.positionY) / 2 - y;
                
                if (relX * relX + relY * relY <= CONN_RADIUS * CONN_RADIUS) {
                    return conn;
                }
            }
        }
        
        return null;
    }
    
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        selectedNode = getNodeAtPosition(mouseX, mouseY);
        
        if (selectedNode != null) {
            dragOffsetX = selectedNode.positionX - mouseX;
            dragOffsetY = selectedNode.positionY - mouseY;
            isDragging = true;
        }
    }
    public void mouseReleased(MouseEvent e) {
        isDragging = false;
    }
    public void mouseClicked(MouseEvent e) {
        if (selectedNode == null) {
            Connection conn = getConnectionAtPosition(e.getX(), e.getY());
            if (conn == null) { // Clicked on background
                String nodeName = gui.showInputDialog("Enter the name of the node to create");
                if (nodeName == null) {return;}
                
                graph.addNode(new Node(nodeName, e.getX(), e.getY()));
                
                gui.repaint();
                return;
            }
            
            // Clicked on connection
            int choice = gui.showOptionDialog("Selected connection between " + conn.getSource().name
                + " and " + conn.getDest().name + "\nWhat would you like to do?",
                new String[] {"Change distance", "Remove", "Cancel"});
            
            switch (choice) {
                case 0:
                    String strDistance = gui.showInputDialog("Enter the distance:");
                    if (strDistance == null) {return;}
                    double distance;
                    try {
                        distance = Double.parseDouble(strDistance);
                    } catch (NumberFormatException ex) {
                        gui.showErrorDialog("Distance is not a number");
                        return;
                    }
                    conn.distance = distance;
                    conn.getOpposite().distance = distance;
                    break;
                case 1:
                    conn.getSource().removeConnection(conn);
                    conn.getDest().removeConnection(conn.getOpposite());
                    break;
                default:
                    return;
            }
            
            gui.repaint();
            return;
        }
        
        // Clicked on node
        int choice = gui.showOptionDialog("Selected " + selectedNode.name + "\nWhat would you like to do?",
            new String[] {"Rename", "Add connection", "Remove node", "Cancel"});
        
        switch (choice) {
            case 0: // Rename
                String name = gui.showInputDialog("Enter the new name:");
                if (name == null) {
                    break;
                }
                selectedNode.name = name;
                break;
            case 1: // Add connection
                String otherName = gui.showInputDialog("Enter the other node's name:");
                if (otherName == null) {return;}
                Node otherNode = graph.getNodeByName(otherName);
                if (otherNode == null) {
                    gui.showErrorDialog("No node by that name exists");
                    return;
                }
                String strDistance = gui.showInputDialog("Enter the distance:");
                if (strDistance == null) {return;}
                double distance;
                try {
                    distance = Double.parseDouble(strDistance);
                } catch (NumberFormatException ex) {
                    gui.showErrorDialog("Distance is not a number");
                    return;
                }
                selectedNode.addConnection(new Connection(selectedNode, otherNode, distance));
                otherNode.addConnection(new Connection(otherNode, selectedNode, distance));
                break;
            case 2: // Remove
                for (Connection conn : selectedNode.getConnections()) {
                    conn.getDest().removeConnection(conn.getOpposite());
                }
                graph.removeNode(selectedNode);
                selectedNode = null;
                break;
            default:
                return;
        }
        
        gui.repaint();
    }
    
    public void mouseMoved(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        
        selectedNode.positionX = mouseX + dragOffsetX;
        selectedNode.positionY = mouseY + dragOffsetY;
        
        gui.repaint();
    }
}