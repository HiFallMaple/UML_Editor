package editor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import main.BroadcastManager;

public class MenuBar extends JMenuBar {
    public MenuBar(){
        // JMenu fileMenu = new JMenu("File");
        // JMenuItem item = new JMenuItem("Open");
        // fileMenu.add(item);
        // this.add(fileMenu);
    
        JMenu editMenu = new JMenu("Edit");
        this.add(editMenu);
    
        JMenuItem item = new JMenuItem("Group");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BroadcastManager.fire("group", "");
            }
        });
        editMenu.add(item);

        item = new JMenuItem("UnGroup");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BroadcastManager.fire("ungroup", "");
            }
        });
        editMenu.add(item);
    
        item = new JMenuItem("change object name");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BroadcastManager.fire("change object name", "");
                
            }
        });
        editMenu.add(item);
    }
    
    
}
