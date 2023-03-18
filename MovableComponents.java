import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MovableComponents extends JPanel {
    private Point originOffset;

    public MovableComponents() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Component parent = _getParent(e);
                System.out.println("Pressed");
                if (parent != null){
                    passEventToParent(e, parent);
                    return;
                }

                originOffset = new Point(e.getX(), e.getY());
            }

        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Component parent = _getParent(e);
                System.out.println("Dragged");
                if (parent != null ){
                    passEventToParent(e, parent);
                    return;
                }
                int dx = e.getX() - originOffset.x;
                int dy = e.getY() - originOffset.y;
                setLocation(getX() + dx, getY() + dy);
            }
        });

    }

    private Component _getParent(MouseEvent e) {
        Component source = (Component) e.getSource();
        Container parent = source.getParent();

        if (parent != null && parent.getName() != "box") {
            return parent;
        }
        return null;
    }

    private void passEventToParent(MouseEvent e, Component parent) {
        Component source = (Component) e.getSource();
        MouseEvent parentEvent = SwingUtilities.convertMouseEvent(source, e, source.getParent());
        parent.dispatchEvent(parentEvent);
    }

}
