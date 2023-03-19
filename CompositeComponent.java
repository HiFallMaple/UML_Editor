import java.awt.Component;
import java.awt.Point;

public class CompositeComponent extends InteractiveComponent {
    // private int padding;

    public CompositeComponent() {
        setOpaque(false);
    }

    @Override
    public boolean contains(int x, int y) {
        Component[] components = getComponents();
        boolean result = false;
        for (Component component : components) {
            Point componentXY = new Point(x - component.getX(), y - component.getY());
            // System.out.println(componentXY);
            result = result | component.contains(componentXY.x, componentXY.y);
        }
        return result;
    }
}
