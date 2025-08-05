/**
 * This class controls the menu and other GUI.
 *
 * Dylan fage-Brown
 * 2025/08/03
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GUI extends JFrame
{
    private Graph graph;
    
    private JMenuBar menuBar;
    private Canvas canvas;
    private JPanel canvasPanel;
    
    public GUI() {
        setTitle("Animating Dijkstra's Algorithm");
        
        Dimension windowSize = new Dimension(1024, 576);
        getContentPane().setPreferredSize(windowSize);
        
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        canvasPanel = new JPanel();
        canvasPanel.setPreferredSize(windowSize);
        canvas = new Canvas();
        canvasPanel.add(canvas);
    }
    public void finishSetup() {
        pack();
        toFront();
        setVisible(true);
    }
    
    public void setGraph(Graph graph) {
        this.graph = graph;
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        
        drawControls(g);
        drawGraph(g, graph);
    }
    
    
    public void addMenu(String name, String[] items, KeyStroke[] shortcuts, ActionListener listener) {
        JMenu menu = new JMenu(name);
        menuBar.add(menu);
        
        for (int i = 0; i < items.length; i++) {
            JMenuItem menuItem = new JMenuItem(items[i]);
            menuItem.addActionListener(listener);
            menuItem.setAccelerator(shortcuts[i]);
            menu.add(menuItem);
        }
    }
    
    
    /*
     * Useful functions for providing feedback and input for the user.
     */
    public void showMessageDialog(String text, String title) {
        JOptionPane.showMessageDialog(this, text, title, JOptionPane.INFORMATION_MESSAGE);
    }
    public void showErrorDialog(String text) {
        JOptionPane.showMessageDialog(this, text, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public String showInputDialog(String text) {
        return JOptionPane.showInputDialog(text);
    }
    public int showOptionDialog(String text, String[] options) {
        return JOptionPane.showOptionDialog(this, text, "Please select",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
    }
    
    
    private void drawControls(Graphics g) {
        g.setColor(Color.black);
        
        if (Dijkstra.isRunning()) {
            // Reset square
            g.drawRect(45, 60, 20, 20);
        }
        // Step triangle
        g.drawLine(15, 60, 35, 70);
        g.drawLine(15, 80, 35, 70);
        g.drawLine(15, 60, 15, 80);
    }
    
    
    private static final int NODE_W = 75;
    private static final int NODE_H = 75;
    
    /*
     * This function works out roughly the width of a string, assuming the characters all have a width of 5.4 and a height of 8
     */
    private void drawCentredString(Graphics g, String str, int x, int y, Color colour) {
        int HALF_W = (int)(str.length() * 2.8);
        int HALF_H = 4;
        g.setColor(colour);
        g.fillRect(x - HALF_W, y - HALF_H, HALF_W * 2, HALF_H * 2);
        g.setColor(Color.black);
        g.drawString(str, x - HALF_W, y + HALF_H);
    }
    
    private void drawGraph(Graphics g, Graph graph) {
        
        // First we draw the connections between nodes
        for (Node node : graph.getNodes()) {
            for (Connection conn : node.getConnections()) {
                Node nodeA = conn.getSource();
                Node nodeB = conn.getDest();
                Color textBGColour;
                if (conn.highlighted) {
                    g.setColor(Color.cyan);
                    textBGColour = Color.cyan;
                } else {
                    g.setColor(Color.black);
                    textBGColour = Color.lightGray;
                }
                g.drawLine(nodeA.positionX, nodeA.positionY, nodeB.positionX, nodeB.positionY);
                drawCentredString(g, "Distance: " + conn.distance, // Halfway between both nodes
                    (nodeA.positionX + nodeB.positionX) / 2, (nodeA.positionY + nodeB.positionY) / 2, textBGColour);
            }
        }
        
        // Then we draw the nodes themselves *on top* of the connections
        for (Node node : graph.getNodes()) {
            Color nodeColour;
            // The highlighting is checked against the current algorithm step so that it will go back after the step is finished.
            if (node.highlightedIdx >= Dijkstra.currIdx) {
                nodeColour = Color.cyan;
            } else if (node.visited) {
                nodeColour = Color.yellow;
            } else {
                nodeColour = Color.lightGray;
            }
            g.setColor(nodeColour);
            g.fillOval(node.positionX - NODE_W / 2, node.positionY - NODE_H / 2, NODE_W, NODE_H);
            
            g.setColor(Color.black);
            g.drawOval(node.positionX - NODE_W / 2, node.positionY - NODE_H / 2, NODE_W, NODE_H);
            
            // Append a string to the name if it's the start or end
            String name = node.name;
            if (node.isStart) {name += " (start)";}
            if (node.isEnd) {name += " (end)";}
            drawCentredString(g, name, node.positionX, node.positionY, nodeColour);
            if (Dijkstra.isRunning()) {
                drawCentredString(g, "Distance: " + node.distance, node.positionX, node.positionY + 10, nodeColour);
            }
        }
        
        
        // Finally, draw the current state
        if (Dijkstra.isRunning()) {
            String stateName = "";
            switch (Dijkstra.currState) {
                case Dijkstra.State.FIND_CLOSEST:
                case Dijkstra.State.FOUND_CLOSEST:
                    stateName = "Find closest unvisited node";
                    break;
                
                case Dijkstra.State.UPDATE_NEIGHBOURS:
                case Dijkstra.State.UPDATED_NEIGHBOURS:
                    stateName = "Update neighbour node's distances";
                    break;
                
                case Dijkstra.State.FIND_PATH:
                    stateName = "End node reached, walk back to the start node";
                    break;
                
                case Dijkstra.State.FOUND_PATH:
                    stateName = "Shortest path found. Press ctrl+R to reset";
                    break;
            }
            g.drawString(stateName, 500, 80);
        }
    }
}