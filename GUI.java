/**
 * This class controls the menu and other GUI.
 *
 * Dylan fage-Brown
 * 2025/07/24
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GUI extends JFrame
{
    private JMenuBar menuBar;
    
    
    public GUI() {
        setTitle("Animating Dijkstra's Algorithm");
        
        getContentPane().setPreferredSize(new Dimension(1024, 576));
        
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
    }
    public void finishSetup() {
        pack();
        toFront();
        setVisible(true);
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
}