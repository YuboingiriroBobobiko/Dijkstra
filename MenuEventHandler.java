/**
 * This class handles all events from the menu bar.
 *
 * Dylan fage-Brown
 * 2025/07/21
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
                
            default:
                System.out.println("Invalid menu option: " + cmdName);
        }
    }
}