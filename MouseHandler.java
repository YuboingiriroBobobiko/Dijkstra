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
        if (selectedNode == null) { // Clicked on background
            return;
        }
        // Otherwise
        
        int choice = gui.showOptionDialog("Selected " + selectedNode.name + "\nWhat would you like to do?",
            new String[] {"Rename", "Add connection", "Remove connection", "Cancel"});
        
        switch (choice) {
            case 0:
                String name = gui.showInputDialog("Enter the new name:");
                if (name == null) {
                    break;
                }
                selectedNode.name = name;
                break;
            case 1:
                break;
            case 2:
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