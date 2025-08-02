/**
 * This class handles all events from the menu bar.
 *
 * Dylan fage-Brown
 * 2025/08/02
 */


import java.awt.event.*;


public class MenuEventHandler implements ActionListener
{
    public MenuEventHandler() {}
    
    public void actionPerformed(ActionEvent event) {
        String cmdName = event.getActionCommand();
        
        // Can't think of an simpler/easier way
        switch (cmdName) {
            // File
            case "New":
                Main.newGraph();
                break;
            case "Open":
                Main.openFileMenu();
                break;
            case "Save":
                Main.saveFileMenu(false);
                break;
            case "Save as":
                Main.saveFileMenu(true);
                break;
            case "Exit":
                System.exit(1);
                break;
            
            // Run
            case "Step":
                Main.stepDijkstra();
                break;
            case "Reset":
                Main.resetDijkstra();
                break;
            
            // Help
            case "Help":
                Main.helpMenu();
                break;
                
            default:
                System.out.println("Invalid menu option: " + cmdName);
        }
    }
}