/**
 * This is the main class of the program and holds the entry point.
 * 
 * The github repository can be found at https://github.com/YuboingiriroBobobiko/Dijkstra
 *
 * Dylan fage-Brown
 * 2025/08/02
 */


import javax.swing.*;
import java.awt.event.*;


public class Main
{
    private static GUI gui;
    
    private static String currentFilename;
    private static Graph currentGraph;
    private static MouseHandler mouseHandler;
    
    public static void main(String[] args) {
        currentGraph = new Graph();
        
        gui = new GUI();
        initMenus();
        gui.finishSetup();
        gui.setGraph(currentGraph);
        
        mouseHandler = new MouseHandler(gui);
        mouseHandler.setGraph(currentGraph);
    }
    
    private static void initMenus() {
        gui.addMenu("File", new String[] {
            "New",
            "Open",
            "Save",
            "Save as",
            "Exit",
        }, new KeyStroke[] {
            KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK),
            KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK),
            KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK),
            KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK),
            KeyStroke.getKeyStroke('Q', InputEvent.CTRL_MASK),
        }, new MenuEventHandler());
        
        gui.addMenu("Run", new String[] {
            "Step",
            "Reset",
        }, new KeyStroke[] {
            KeyStroke.getKeyStroke(' '),
            KeyStroke.getKeyStroke('R', InputEvent.CTRL_MASK),
        }, new MenuEventHandler());
        
        gui.addMenu("Help", new String[] {
            "Help",
        }, new KeyStroke[] {
            KeyStroke.getKeyStroke('H', InputEvent.CTRL_MASK),
        }, new MenuEventHandler());
    }
    
    
    public static void newGraph() {
        currentFilename = null;
        currentGraph = new Graph();
        
        mouseHandler.setGraph(currentGraph);
        gui.setGraph(currentGraph);
        gui.repaint();
    }
    
    public static void openFileMenu() {
        String name = gui.showInputDialog("Choose a filename:");
        if (name == null) {
            return;
        }
        currentFilename = name;
        
        Graph newGraph = FileInterface.readGraph(currentFilename, gui);
        if (newGraph != null) {
            currentGraph = newGraph;
            gui.setGraph(newGraph);
            mouseHandler.setGraph(newGraph);
            
            gui.repaint(); // Update the graphics to show the new graph
        }
    }
    
    public static void saveFileMenu(boolean askForName) {
        // Can't save if we don't have a name
        if (currentFilename == null) {
            askForName = true;
        }
        
        if (askForName) {
            String name = gui.showInputDialog("Choose a filename:");
            if (name == null) {
                return;
            }
            
            currentFilename = name;
        }
        
        FileInterface.writeGraph(currentFilename, currentGraph, gui);
    }
    
    
    public static void helpMenu() {
        gui.showMessageDialog("To open an example graph, go to File > Open and enter \"example.csv\".\nPress space to step forward in the simulation.", "Help");
    }
    
    
    /*
     * In-between function because MenuEventHandler does not have the current graph.
     * Also checks if we have a graph.
     */
    public static void stepDijkstra() {
        if (currentGraph == null) {return;}
        
        Dijkstra.step(currentGraph, gui);
        gui.repaint();
    }
    public static void resetDijkstra() {
        if (currentGraph == null) {return;}
        
        Dijkstra.reset(currentGraph);
        gui.repaint();
    }
}