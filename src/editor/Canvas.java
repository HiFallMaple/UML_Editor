package editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import component.Shape;
import component.basicObject.CompositeObject;
import main.Config;

public class Canvas extends PaddingPanel {
    private static Canvas instance;
    private static int padding = Config.getIntProperty("area.padding");
    private Frame frame;
    protected ArrayList<Shape> shapeList;

    private Canvas() {
        super(padding, padding, padding, padding);
        addBox(new JPanel());
        shapeList = new ArrayList<Shape>();
        this.box.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.box.setLayout(null);
        this.box.setName("Box");
        CanvasMouseListener listener = CanvasMouseListener.getInstance();
        this.box.addMouseListener(listener);
        this.box.addMouseMotionListener(listener);
    }

    public static synchronized Canvas getInstance() {
        if (instance == null) {
            instance = new Canvas();
        }
        return instance;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public void refresh() {
        this.box.revalidate();
        this.box.repaint();
    }

    public void addObject(Shape object) {
        this.box.add(object, 0);
        this.shapeList.add(object);
    }

    public void removeObject(Shape object) {
        this.box.remove(object);
        this.shapeList.remove(object);
    }

    /**
     * 將給定的座標，從與 canvas 的相對座標，轉換成與 component 的相對座標
     * 
     * @param component
     * @param point
     * @return Point
     */
    public Point convertPointToComponent(Component component, Point point) {
        return SwingUtilities.convertPoint(this.box, point, component);
    }

    /**
     * 將給定的座標，從與 component 的相對座標，轉換成與 canvas 的相對座標
     * 
     * @param component
     * @param point
     * @return Point
     */
    public Point convertPointToCanvas(Component component, Point point) {
        return SwingUtilities.convertPoint(component, point, this.box);
    }

    public void groupObject() {
        ArrayList<Shape> list = new ArrayList<Shape>();
        for (Shape component : this.shapeList) {
            if (component.isSelected()) {
                list.add(component);
            }
        }
        if (list.size() > 1) { // 如果select的大於一個，才進行group
            Collections.reverse(list);
            for (Shape object : list) {
                object.unselect();
                this.removeObject(object);
            }
            Shape group = new CompositeObject(list);
            this.addObject(group);
            group.moveLineToTop();
        }
    }

    public void ungroupObject() {
        int count = 0;
        Shape object = null;
        for (Shape component : this.shapeList) {
            if (component.isSelected()) {
                count++;
                object = component;
            }
        }
        if (count == 1) { // 如果select等於一個，才進行ungroup
            object.ungroup();
            this.removeObject(object);
        }
    }

    public void selectRange(Point leftTop, Point rightDown) {
        for (Shape object : this.shapeList) {
            int width = object.getWidth();
            int height = object.getHeight();
            Point location = object.getLocation();
            boolean up = location.y > leftTop.y;
            boolean left = location.x > leftTop.x;
            boolean down = (location.y + height) < rightDown.y;
            boolean right = (location.x + width) < rightDown.x;
            if (up && left && down && right) {
                object.select();
            }
        }
    }

    public void unselectAll() {
        for (Shape object : this.shapeList) {
            object.unselect();
        }
    }

    public void changeObjectName() {
        int count = 0;
        Shape object = null;
        for (Shape component : this.shapeList) {
            if (component.isSelected()) {
                count++;
                object = component;
            }
        }
        if (count == 1) { // 如果select等於一個，才進行ungroup
            ChangeNameDialog dialog = new ChangeNameDialog(frame);
            dialog.setVisible(true);
            if(dialog.isOk()){
                String text = dialog.getText();
                object.setName(text);
            }
        }
    }

    /**
     * 取得 point 座標的 Basic Object 物件
     * 
     * @param point 座標點
     * @return Shape BasicObject
     */
    public Shape getBasicObjectAt(Point point) {
        Shape object = (Shape) this.box.getComponentAt(point.x, point.y);
        if (object != null)
            return object.getObjectAt(point);
        else
            return null;
    }

    @Override
    public Component getComponentAt(int x, int y) {
        Component component = this.box.getComponentAt(x, y);
        if (component == this.box)
            return null;
        else
            return component;
    }
}
