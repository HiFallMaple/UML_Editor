package component.basicObject;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import component.Shape;

public class CompositeObject extends Shape {
    protected ArrayList<Shape> shapeList;

    public CompositeObject(ArrayList<Shape> components) {
        super();
        setOpaque(false);
        setLayout(null);
        this.shapeList = components;
        calBounds();
        addComponents();
    }

    @Override
    public boolean contains(int x, int y) {
        boolean result = false;
        for (Shape shape : this.shapeList) {
            Point componentXY = new Point(x - shape.getX(), y - shape.getY());
            result = result | shape.contains(componentXY.x, componentXY.y);
        }
        return result;
    }

    protected void calBounds() {
        int up = Integer.MAX_VALUE;
        int left = Integer.MAX_VALUE;
        int down = 0;
        int right = 0;
        for (Shape shape : this.shapeList) {
            int width = shape.getWidth();
            int height = shape.getHeight();
            Point location = shape.getLocation();
            up = Math.min(up, location.y);
            left = Math.min(left, location.x);
            down = Math.max(down, location.y + height);
            right = Math.max(right, location.x + width);
        }
        this.width = right - left;
        this.height = down - up;
        setBounds(left, up, width, height);
    }

    protected void addComponents() {
        for (Shape shape : this.shapeList) {
            Point l = shape.getLocation();
            shape.setLocation(l.x - getX(), l.y - getY());
            add(shape, 0);
        }
    }

    @Override
    public void ungroup() {
        for (Shape shape : this.shapeList) {
            Point l = shape.getLocation();
            shape.setLocation(l.x + getX(), l.y + getY());
            shape.unselect();
            canvas.addObject(shape);
        }
        moveLineToTop();
    }

    @Override
    public void moveLineToTop() {
        for (Shape shape : this.shapeList) {
            shape.moveLineToTop();
        }
    }

    @Override
    public void select() {
        for (Shape shape : this.shapeList) {
            shape.select();
        }
        super.select();
    }

    @Override
    public void unselect() {
        for (Shape shape : this.shapeList) {
            shape.unselect();
        }
        super.unselect();

    }

    @Override
    public void toggleSelect() {
        if (selected) {
            unselect();
        } else {
            select();
        }
    }

    @Override
    public Shape getObjectAt(Point point) {
        Point newPoint = SwingUtilities.convertPoint(this.getParent(),
                point, this);
        Shape object = (Shape) this.getComponentAt(newPoint.x, newPoint.y);
        if (object != null)
            return object.getObjectAt(newPoint);
        else
            return null;
    }

}
