package component.line;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import component.Shape;

import java.awt.Dimension;
import java.awt.Graphics;

import main.Config;
import main.Direction;

public class Line extends Shape {
    /**
     * 相對於自身的起始點座標
     */
    protected Point tail;
    /**
     * 相對於 canvas 的起始點的座標
     */
    protected Point tailInParent;
    /**
     * 相對於自身的終點座標
     */
    protected Point head;
    /**
     * 相對於 canvas 的終點座標
     */
    protected Point headInParent;
    protected Point location;
    protected Dimension size;
    protected Color color;
    protected int padding;
    protected int lineWidth;
    protected LineType type;
    protected ArrayList<Shape> connectShape;

    public Line(LineType type) {
        super();
        init(type, Color.BLACK, 2);
    }

    public Line(LineType type, Point tail, Point head) {
        super();
        init(type, Color.BLACK, 2);
        setSizeLocation(tail, head);
    }

    private void init(LineType type, Color color, int lineWidth) {
        this.connectShape = new ArrayList<Shape>();
        this.type = type;
        this.padding = Config.getIntProperty("cl.boxpadding");
        this.lineWidth = lineWidth;
        this.color = color;
        this.setOpaque(false);
    }

    /**
     * 給定 tail 與 head 座標，更新此物件大小與位置
     */
    public void setSizeLocation(Point tail, Point head) {
        this.location = calLocation(tail, head);
        this.size = calSize(tail, head);
        // 設定相對於 canvas 的座標
        this.tailInParent = tail;
        this.headInParent = head;
        // 設定相對於自身的座標
        this.tail = this.pointMinus(tail, this.location);
        this.head = this.pointMinus(head, this.location);
        this.setSize(this.size);
        this.setLocation(location);
    }

    protected Point calLocation(Point tail, Point head) {
        int x = Math.min(tail.x, head.x) - this.padding;
        int y = Math.min(tail.y, head.y) - this.padding;
        Point location = new Point(x, y);
        return location;
    }

    protected Dimension calSize(Point tail, Point head) {
        Dimension size = new Dimension(Math.abs(tail.x - head.x) + this.padding * 2,
                Math.abs(tail.y - head.y) + this.padding * 2);
        return size;
    }

    /**
     * 將 a 與 b 座標相減
     * 
     * @param a
     * @param b
     * @return Point(a.x - b.x, a.y - b.y)
     */
    protected Point pointMinus(Point a, Point b) {
        return new Point(a.x - b.x, a.y - b.y);
    }
    
    /**
     * 設定自身頭的座標
     * 
     * @param head
     */
    public void setHeadPoint(Point head) {
        setSizeLocation(this.tailInParent, head);
        repaint();
    }
    
    /**
    * 設定自身尾巴的座標
    * 
     * @param tail
     */
    public void setTailPoint(Point tail) {
        setSizeLocation(tail, this.headInParent);
        repaint();
    }

    /**
     * 根據給定的 direction，更新自身座標與大小
     * 
     * @param point     座標
     * @param direction 頭或尾
     */
    public void setPoint(Point point, boolean direction) {
        if (direction == Direction.TAIL) {
            setTailPoint(point);
            // System.out.println("Set tail!!");
        } else {
            setHeadPoint(point);
            // System.out.println("Set head!!");
        }
    }

    /**
     * <p>
     * 根據傳入的座標，將 Line 與 Object 的 port 綁定，並回傳 port 座標
     * </p>
     * @param objects
     * @param line
     * @param pressPoint
     * @param releasePoint
     * @return point回傳為 {head port point, tail port point}
     */
    public void bindLineToObject(Shape object, int port, boolean direction) {
        // 將綁定物件儲存至 List
        this.connectShape.add(object);

        // 將連接線的引用傳遞給 object
        object.addLine(this, port, direction);
    }

    @Override
    public void removeFromCanvas(){
        super.removeFromCanvas();
        for(Shape object: this.connectShape){
            object.removeLine(this);
        }
    }

    @Override
    public boolean contains(int x, int y) {
        return false;
    }

    @Override
    public Shape getObjectAt(Point point) {
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.draw(g);
    }

    protected void draw(Graphics g) {
        this.type.color = this.color;
        this.type.draw(g, tail, head);
    }
}
