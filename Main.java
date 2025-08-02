/**
 * This is the main class of the program and holds the entry point.
 *
 * Dylan fage-Brown
 * 2025/07/24
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
            "Open",
            "Save",
            "Save as",
            "Exit",
        }, new KeyStroke[] {
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
            KeyStroke.getKeyStroke('r'),
        }, new MenuEventHandler());
    }
    
    // This exists only for the GUI and MouseHandler classes to use.
    // Otherwise I'd need to recreate it each time I load? Or send it to them?
    public static Graph getGraph() {
        return currentGraph;
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
}