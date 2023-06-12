package mode;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;

import component.Shape;

public class SelectMode extends Mode {
    private Shape pressComponent = null;
    private Point originOffset = null;

    public SelectMode() {
        super();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        Component component = canvas.getComponentAt(pressPoint.x, pressPoint.y);
        if (component != null) {
            pressComponent = (Shape) component;
            Point location = pressComponent.getLocation();
            originOffset = new Point(location.x - pressPoint.x, location.y - pressPoint.y);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        pressComponent = null;
        if (isDragged) {
            isDragged = false;
            canvas.unselectAll();
            Point leftTop = new Point(Math.min(pressPoint.x, releasePoint.x),
                    Math.min(pressPoint.y, releasePoint.y));
            Point rightDown = new Point(Math.max(pressPoint.x, releasePoint.x),
                    Math.max(pressPoint.y, releasePoint.y));
            canvas.selectRange(leftTop, rightDown);
            canvas.refresh();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        if (pressComponent != null) {
            pressComponent.setLocation(e.getX() + originOffset.x, e.getY() + originOffset.y);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        Component component = canvas.getComponentAt(clickPoint.x, clickPoint.y);
        if (component != null) {
            Shape object = (Shape) component;
            boolean isSelected = object.isSelected();
            canvas.unselectAll();
            if (!isSelected) {
                object.select();
            }

        } else {
            canvas.unselectAll();
        }
    }

}
