import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class EditorPanel extends PaddingPanel {

    private ArrayList<InteractiveComponent> interactiveComponentsList;
    private ArrayList<CompositeComponent> compositeComponentsList;
    private JFrame frame;

    public EditorPanel(int padding, JFrame frame) {
        super(padding, padding, padding, padding);
        addBox(new JPanel());
        interactiveComponentsList = new ArrayList<InteractiveComponent>();
        compositeComponentsList = new ArrayList<CompositeComponent>();
        this.frame = frame;
        // this.setLayout(null);
        box.setName("Canvas");
        box.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        box.setLayout(null);
        MouseAdapter listener = new CanvasMouseListener();
        box.addMouseListener(listener);
        box.addMouseMotionListener(listener);
        this.add(box, BorderLayout.CENTER);

        BroadcastManager.subListener(new GroupListener());
    }

    private class GroupListener implements BroadcastListener {
        @Override
        public void handle(String eventName, String message) {
            if (eventName == "group") {
                System.out.println("toggle group listener");
                groupComponents();
            } else if (eventName == "ungroup") {
                System.out.println("toggle ungroup listener");
                unGroupComponents();
            } else if (eventName == "change object name") {
                System.out.println("toggle change name listener");
                changeObjectName();
            }
        }
    }

    private void groupComponents() {
        ArrayList<InteractiveComponent> group = new ArrayList<InteractiveComponent>();
        for (Component component : this.box.getComponents()) {
            if (component instanceof InteractiveComponent) {
                if (((InteractiveComponent) component).isSelected()) {
                    group.add((InteractiveComponent) component);
                }
            }
        }
        if (group.size() > 1) { // 如果select的大於一個，才進行group
            Collections.reverse(group);
            for (InteractiveComponent component : group) {
                component.unselect();
                removeInteractiveComponent(component);
                if (component instanceof CompositeComponent) {
                    removeCompositeComponent((CompositeComponent) component);
                } else {
                    removeInteractiveComponent(component);

                }
            }
            addCompositeComponent(new CompositeComponent(box, group));
        }
    }

    private void unGroupComponents() {
        int selectedCount = 0;
        CompositeComponent group = null;
        for (CompositeComponent component : compositeComponentsList) {
            if (component.isSelected()) {
                group = component;
                selectedCount++;
            }
        }
        if (selectedCount == 1) {
            for (InteractiveComponent component : group.interactiveComponents) {
                Point l = component.getLocation();
                component.setLocation(l.x + group.location.x, l.y + group.location.y);
                component.unselect();
                if (component instanceof CompositeComponent) {
                    addCompositeComponent((CompositeComponent) component);
                } else {
                    addInteractiveComponent(component);

                }
            }
            removeCompositeComponent(group);
        }
    }

    private void changeObjectName() {
        int selectedCount = 0;
        InteractiveComponent selectedComponent = null;
        for (InteractiveComponent component : interactiveComponentsList) {
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

    private void refresh() {
        this.box.revalidate();
        this.box.repaint();
    }

    private void addComponent(Component component) {
        this.box.add(component, 0);
    }

    private void removeComponent(Component component) {
        this.box.remove(component);
    }

    private void addInteractiveComponent(InteractiveComponent component) {
        addComponent(component);
        interactiveComponentsList.add(component);
    }

    private void removeInteractiveComponent(InteractiveComponent component) {
        removeComponent(component);
        interactiveComponentsList.remove(component);
    }

    private void addCompositeComponent(CompositeComponent component) {
        addInteractiveComponent(component);
        compositeComponentsList.add(component);
    }

    private void removeCompositeComponent(CompositeComponent component) {
        removeInteractiveComponent(component);
        compositeComponentsList.remove(component);
    }

    private void addComponentByStatus(int status, Point location) {
        InteractiveComponent component = null;
        if (status == Mode.CLASS) {
            component = new ClassComponent(this.box);
        } else if (status == Mode.USE_CASE) {
            component = new UseCaseComponent(this.box);
        }
        component.setLocation(location);
        addInteractiveComponent(component);
        refresh();
    }

    private class CanvasMouseListener extends MouseAdapter {
        private Point pressPoint = null;
        private Point releasePoint = null;
        private boolean isDragged = false;

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println("Pressed canvas");
            pressPoint = new Point(e.getX(), e.getY());
            System.out.println(pressPoint);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // 如果拖動的話就跳出
            releasePoint = new Point(e.getX(), e.getY());
            System.out.println("Released canvas");
            System.out.println(releasePoint);
            if (isDragged) {
                isDragged = false;
                if (Mode.isConnectionLineMode()) {
                    BaseObject[] baseObjects = {
                            getBaseObject(pressPoint, EditorPanel.this.box.getComponentAt(pressPoint.x, pressPoint.y)), // 尾巴
                            getBaseObject(releasePoint,
                                    EditorPanel.this.box.getComponentAt(releasePoint.x, releasePoint.y)), // 頭
                    };

                    if (baseObjects[0] != null && baseObjects[1] != null) {
                        addConnectionLine(Mode.getStatus(), baseObjects);
                    }
                } else if (Mode.getStatus() == Mode.SELECT) {
                    // Component[] components = getComponents();
                    BroadcastManager.fire("unselect", "");
                    Point leftTop = new Point(Math.min(pressPoint.x, releasePoint.x),
                            Math.min(pressPoint.y, releasePoint.y));
                    Point rightDown = new Point(Math.max(pressPoint.x, releasePoint.x),
                            Math.max(pressPoint.y, releasePoint.y));
                    for (InteractiveComponent component : interactiveComponentsList) {
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
                return;
            }
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

        public void addConnectionLine(int status, BaseObject[] objects) {
            ConnectionLine line = null;
            if (status == Mode.ASSOCIATION) {
                line = new AssociationLine(pressPoint, releasePoint, Color.BLACK, 2);
            } else if (status == Mode.GENERALIZATION) {
                line = new GeneralizationLine(pressPoint, releasePoint, Color.BLACK, 2);
            } else if (status == Mode.COMPOSITION) {
                line = new CompositionLine(pressPoint, releasePoint, Color.BLACK, 2);
            } else {
                return;
            }
            EditorPanel.this.addComponent(line);
            EditorPanel.this.refresh();
            Point[] relativePoint = new Point[2];
            int[] direction = new int[2];
            for (int i = 0; i < 2; i++) {
                relativePoint[i] = SwingUtilities.convertPoint(EditorPanel.this.box,
                        i == 0 ? pressPoint : releasePoint, objects[i]);
                direction[i] = objects[i].getPortDirection(relativePoint[i]);
                objects[i].addConnectionLine(line, direction[i], i == 0 ? Direction.TAIL : Direction.HEAD);
                Point location = SwingUtilities.convertPoint(objects[i],
                        objects[i].getConnectionPortPoint(direction[i]), EditorPanel.this.box);
                line.setPoint(location, i == 0 ? Direction.TAIL : Direction.HEAD);
            }
            EditorPanel.this.box.revalidate();
            EditorPanel.this.box.repaint();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            Point clickPoint = new Point(e.getX(), e.getY());
            System.out.println("Click canvas");
            System.out.println(clickPoint);
            if (Mode.getStatus() == Mode.SELECT) {
                BroadcastManager.fire("unselect", "");
            } else if (Mode.getStatus() == Mode.CLASS || Mode.getStatus() == Mode.USE_CASE) {
                EditorPanel.this.addComponentByStatus(Mode.getStatus(), clickPoint);
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            isDragged = true;
            Point clickPoint = new Point(e.getX(), e.getY());
            System.out.println("Dragged canvas");
            System.out.println(clickPoint);
        }

    }

}
