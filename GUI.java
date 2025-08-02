/**
 * This class controls the menu and other GUI.
 *
 * Dylan fage-Brown
 * 2025/07/31
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
    
    
    private static final int NODE_W = 75;
    private static final int NODE_H = 75;
    
    private void drawCentredString(Graphics g, String str, int x, int y) {
        int HALF_W = (int)(str.length() * 2.8);
        int HALF_H = 4;
        g.setColor(Color.lightGray);
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
                g.setColor(Color.black);
                g.drawLine(nodeA.positionX, nodeA.positionY, nodeB.positionX, nodeB.positionY);
                drawCentredString(g, "Distance: " + conn.distance, // Halfway between both nodes
                    (nodeA.positionX + nodeB.positionX) / 2, (nodeA.positionY + nodeB.positionY) / 2);
            }
        }
        
        // Then we draw the nodes themselves *on top* of the connections
        for (Node node : graph.getNodes()) {
            g.setColor(Color.lightGray);
            g.fillOval(node.positionX - NODE_W / 2, node.positionY - NODE_H / 2, NODE_W, NODE_H);
            g.setColor(Color.black);
            g.drawOval(node.positionX - NODE_W / 2, node.positionY - NODE_H / 2, NODE_W, NODE_H);
            drawCentredString(g, node.name, node.positionX, node.positionY);
        }
    }
}