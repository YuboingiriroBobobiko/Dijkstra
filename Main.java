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
    
    public static void main(String[] args) {
        currentGraph = new Graph();
        
        gui = new GUI();
        
        initMenus();
        
        gui.finishSetup();
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
    }
    
    public static GUI getGui() {
        return gui;
    }
    public static Graph getGraph() {
        return currentGraph;
    }
    
    
    public static void openFileMenu() {
        String name = gui.showInputDialog("Choose a filename");
        if (name == null) {
            return;
        }
        currentFilename = name;
        
        currentGraph = FileInterface.readGraph(currentFilename);
        
        gui.repaint(); // Update the graphics to show the new graph
    }
    
    public static void saveFileMenu(boolean askForName) {
        // Can't save if we don't have a name
        if (currentFilename == null) {
            askForName = true;
        }
        
        if (askForName) {
            String name = gui.showInputDialog("Choose a filename");
            if (name == null) {
                return;
            }
            
            currentFilename = name;
        }
        
        FileInterface.writeGraph(currentFilename, currentGraph);
    }
}