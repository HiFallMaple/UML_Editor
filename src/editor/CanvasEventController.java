package editor;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import component.BaseObject;
import component.CompositeComponent;
import component.InteractiveComponent;
import main.BroadcastListener;
import main.BroadcastManager;
import main.Mode;

public class CanvasEventController {
    private EditorPanel panel;
    private JPanel canvas;
    private JFrame frame;
    public CanvasEventController(EditorPanel panel, JFrame frame){
        this.panel = panel;
        this.canvas = panel.box;
        this.frame = frame;

        MouseAdapter listener = new CanvasMouseListener();
        canvas.addMouseListener(listener);
        canvas.addMouseMotionListener(listener);
        BroadcastManager.subListener(new GroupListener());
    }

    private class GroupListener implements BroadcastListener {
        @Override
        public void handle(String eventName, String message) {
            if (eventName == "group") {
                // System.out.println("toggle group listener");
                groupComponents();
            } else if (eventName == "ungroup") {
                // System.out.println("toggle ungroup listener");
                unGroupComponents();
            } else if (eventName == "change object name") {
                // System.out.println("toggle change name listener");
                changeObjectName();
            }
        }
    }

    private void groupComponents() {
        ArrayList<InteractiveComponent> list = new ArrayList<InteractiveComponent>();
        for (Component component : canvas.getComponents()) {
            if (component instanceof InteractiveComponent) {
                if (((InteractiveComponent) component).isSelected()) {
                    list.add((InteractiveComponent) component);
                }
            }
        }
        if (list.size() > 1) { // 如果select的大於一個，才進行group
            Collections.reverse(list);
            for (InteractiveComponent component : list) {
                component.unselect();
                panel.removeInteractiveComponent(component);
                if (component instanceof CompositeComponent) {
                    panel.removeCompositeComponent((CompositeComponent) component);
                } else {
                    panel.removeInteractiveComponent(component);

                }
            }
            CompositeComponent group = new CompositeComponent(canvas, list);
            panel.addCompositeComponent(group);
            group.moveLineToTop();
        }
    }

    private void unGroupComponents() {
        int selectedCount = 0;
        CompositeComponent group = null;
        for (CompositeComponent component : panel.compositeComponentsList) {
            if (component.isSelected()) {
                group = component;
                selectedCount++;
            }
        }
        if (selectedCount == 1) {
            for (InteractiveComponent component : group.interactiveComponents) {
                Point l = component.getLocation();
                component.setLocation(l.x + group.getX(), l.y + group.getY());
                component.unselect();
                if (component instanceof CompositeComponent) {
                    panel.addCompositeComponent((CompositeComponent) component);
                } else {
                    panel.addInteractiveComponent(component);

                }
            }
            panel.removeCompositeComponent(group);
            group.moveLineToTop();
        }
    }

    private void changeObjectName() {
        int selectedCount = 0;
        InteractiveComponent selectedComponent = null;
        for (InteractiveComponent component : panel.interactiveComponentsList) {
            if (component.isSelected()) {
                selectedComponent = component;
                selectedCount++;
            }
        }
        if (selectedCount == 1) {
            if (selectedComponent instanceof BaseObject) {
                ChangeNameDialog dialog = new ChangeNameDialog(frame);
                dialog.setVisible(true);
                selectedComponent.setName(dialog.getText());
                // selectedComponent
            }
        }
    }

    private class CanvasMouseListener extends MouseAdapter {
        private Point pressPoint = null;
        private Point releasePoint = null;
        private Point originOffset = null;
        private Component pressComponent = null;
        private boolean isDragged = false;

        @Override
        public void mousePressed(MouseEvent e) {
            // System.out.println("Pressed canvas");
            pressPoint = new Point(e.getX(), e.getY());
            // System.out.println(pressPoint);
            if (Mode.getStatus() == Mode.SELECT) {
                pressComponent = canvas.getComponentAt(pressPoint.x, pressPoint.y);
                if (pressComponent.getName() != "Canvas"){
                    Point location = pressComponent.getLocation();
                    originOffset =  new Point(location.x - pressPoint.x, location.y - pressPoint.y);
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            releasePoint = new Point(e.getX(), e.getY());
            pressComponent = null;
            originOffset = null;
            // System.out.println("Released canvas");
            // System.out.println(releasePoint);
            if (isDragged) {
                isDragged = false;
                if (Mode.isConnectionLineMode()) {
                    BaseObject[] baseObjects = {
                            getBaseObject(pressPoint, canvas.getComponentAt(pressPoint.x, pressPoint.y)), // 尾巴
                            getBaseObject(releasePoint,
                                    canvas.getComponentAt(releasePoint.x, releasePoint.y)), // 頭
                    };

                    if (baseObjects[0] != null && baseObjects[1] != null) {
                        panel.addConnectionLine(Mode.getStatus(), baseObjects, pressPoint, releasePoint);
                    }
                } else if (Mode.getStatus() == Mode.SELECT) {
                    // Component[] components = getComponents();
                    BroadcastManager.fire("unselect", "");
                    Point leftTop = new Point(Math.min(pressPoint.x, releasePoint.x),
                            Math.min(pressPoint.y, releasePoint.y));
                    Point rightDown = new Point(Math.max(pressPoint.x, releasePoint.x),
                            Math.max(pressPoint.y, releasePoint.y));
                    for (InteractiveComponent component : panel.interactiveComponentsList) {
                        int width = component.getWidth();
                        int height = component.getHeight();
                        Point location = component.getLocation();
                        boolean up = location.y > leftTop.y;
                        boolean left = location.x > leftTop.x;
                        boolean down = (location.y + height) < rightDown.y;
                        boolean right = (location.x + width) < rightDown.x;
                        if (up && left && down && right) {
                            component.select();
                        }
                    }
                }
            }
            return;
        }

        public BaseObject getBaseObject(Point point, Component component) {
            if (component instanceof BaseObject) {
                return (BaseObject) component;
            } else if (component instanceof CompositeComponent) {
                Point newPoint = SwingUtilities.convertPoint(component.getParent(),
                        point, component);
                return getBaseObject(newPoint, component.getComponentAt(newPoint.x, newPoint.y));
            } else {
                return null;
            }
        }

        

        @Override
        public void mouseClicked(MouseEvent e) {
            Point clickPoint = new Point(e.getX(), e.getY());
            // System.out.println("Click canvas");
            // System.out.println(clickPoint);
            if (Mode.getStatus() == Mode.SELECT) {
                Component component = canvas.getComponentAt(clickPoint.x, clickPoint.y);
                if(component != null){
                    if(component instanceof InteractiveComponent){
                        ((InteractiveComponent)component).toggleSelect();
                    }
                }else{
                    BroadcastManager.fire("unselect", "");
                }
            } else if (Mode.getStatus() == Mode.CLASS || Mode.getStatus() == Mode.USE_CASE) {
                panel.addBaseObject(Mode.getStatus(), clickPoint);
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            isDragged = true;
            // System.out.println("Dragged canvas");
            // System.out.println(draggedPoint);
            if (Mode.getStatus() == Mode.SELECT){
                if (pressComponent.getName() != "Canvas"){
                    pressComponent.setLocation(e.getX() + originOffset.x, e.getY() + originOffset.y);
                }
            }
        }

    }
}
