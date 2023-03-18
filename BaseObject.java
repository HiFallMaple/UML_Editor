import java.awt.Component;
import java.awt.Point;

public class BaseObject extends MovableComponents {
    // private Config config = Config.getInstance();
    // private int padding;

    public BaseObject() {
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
