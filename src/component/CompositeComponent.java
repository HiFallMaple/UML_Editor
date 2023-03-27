package component;
import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

public class CompositeComponent extends InteractiveComponent {
    public ArrayList<InteractiveComponent> interactiveComponents;

    public CompositeComponent(JPanel canvas, ArrayList<InteractiveComponent> components) {
        super(canvas);
        setOpaque(false);
        setLayout(null);
        this.interactiveComponents = components;
        calSize();
        addComponents();
        setBounds(getX(), getY(), width, height);
    }

    @Override
    public boolean contains(int x, int y) {
        Component[] components = getComponents();
        boolean result = false;
        for (Component component : components) {
            Point componentXY = new Point(x - component.getX(), y - component.getY());
            result = result | component.contains(componentXY.x, componentXY.y);
        }
        return result;
    }

    public void calSize() {
        int up = 0;
        int left = 0;
        int down = 0;
        int right = 0;
        // Component[] components = getComponents();
        for (Component component : interactiveComponents) {
            int width = component.getWidth();
            int height = component.getHeight();
            Point location = component.getLocation();
            up = Math.min(up, location.y);
            left = Math.min(left, location.x);
            down = Math.max(down, location.y + height);
            right = Math.max(right, location.x + width);
        }
        this.width = right - left;
        this.height = down - up;
    }

    private void addComponents() {
        for (Component component : interactiveComponents) {
            Point l = component.getLocation();
            component.setLocation(l.x - getX(), l.y - getY());
            add(component, 0);
        }
    }

    public void removeComponents() {
        // for (Component component : interactiveComponents) {
        for (Component component : interactiveComponents) {
            Point l = component.getLocation();
            component.setLocation(l.x + getX(), l.y + getY());
            canvas.add(component);
        }
    }

    @Override
    public void select() {
        selected = true;
        for (InteractiveComponent component : interactiveComponents) {
            component.select();
        }
        revalidate();
        repaint();
    }

    @Override
    public void unselect() {
        selected = false;
        for (InteractiveComponent component : interactiveComponents) {
            component.unselect();
        }
        revalidate();
        repaint();
    }

    @Override
    public void toggleSelect() {
        if (selected) {
            unselect();
        } else {
            select();
        }
        revalidate();
        repaint();
    }

}
