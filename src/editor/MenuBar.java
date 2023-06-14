package editor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import editor.canvasArea.Canvas;

public class MenuBar extends JMenuBar {
    private Canvas canvas;

    public MenuBar(){    
        this.canvas = Canvas.getInstance();

        JMenu editMenu = new JMenu("Edit");
        this.add(editMenu);
    
        JMenuItem item = new AntiAliasedMenuItem("Group");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.groupObject();
            }
        });
        editMenu.add(item);

        item = new AntiAliasedMenuItem("UnGroup");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.ungroupObject();
            }
        });
        editMenu.add(item);
    
        item = new AntiAliasedMenuItem("change object name");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.changeObjectName();
                
            }
        });
        editMenu.add(item);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //抗鋸齒
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g2d);
    }

    private class AntiAliasedMenuItem extends JMenuItem {
        
        public AntiAliasedMenuItem(String text) {
            super(text);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            super.paintComponent(g);
        }
    }
    
}
