import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {
    public MenuBar(){
        JMenu Menu = new JMenu("File");
        this.add(Menu);
        JMenuItem Item = new JMenuItem("Open");
        Menu.add(Item);
        // fileMenu.addSeparator();
        Menu = new JMenu("Edit");
        this.add(Menu);
        Menu.add(Item);
        Item = new JMenuItem("Group");
        Menu.add(Item);
        Item = new JMenuItem("change object name");
        Menu.add(Item);
    }
    
}
