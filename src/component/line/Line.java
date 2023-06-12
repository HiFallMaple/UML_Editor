package component.line;

import java.awt.Color;
import java.awt.Point;

import component.Shape;

import java.awt.Dimension;
import java.awt.Graphics;

import main.Config;
import main.Direction;

public class Line extends Shape {
    /**
     * 相對於自身的起始點座標
     */
    protected Point head;
    /**
     * 相對於 canvas 的起始點的座標
     */
    protected Point headInParent;
    /**
     * 相對於自身的終點座標
     */
    protected Point tail;
    /**
     * 相對於 canvas 的終點座標
     */
    protected Point tailInParent;
    protected Point location;
    protected Dimension size;
    protected Color color;
    protected int padding;
    protected int lineWidth;
    protected LineType type;

    public Line(LineType type) {
        super();
        init(type, Color.BLACK, 2);
    }

    public Line(LineType type, Point head, Point tail) {
        super();
        init(type, Color.BLACK, 2);
        setSizeLocation(head, tail);
    }

    private void init(LineType type, Color color, int lineWidth) {
        this.type = type;
        this.padding = Config.getIntProperty("cl.boxpadding");
        this.lineWidth = lineWidth;
        this.color = color;
        this.setOpaque(false);
    }

    /**
     * 給定 head 與 tail 座標，更新此物件大小與位置
     */
    public void setSizeLocation(Point head, Point tail) {
        this.location = calLocation(head, tail);
        this.size = calSize(head, tail);
        // 設定相對於 canvas 的座標
        this.headInParent = head;
        this.tailInParent = tail;
        // 設定相對於自身的座標
        this.head = this.pointMinus(head, this.location);
        this.tail = this.pointMinus(tail, this.location);
        this.setSize(this.size);
        this.setLocation(location);
    }

    protected Point calLocation(Point head, Point tail) {
        int x = Math.min(head.x, tail.x) - this.padding;
        int y = Math.min(head.y, tail.y) - this.padding;
        Point location = new Point(x, y);
        return location;
    }

    protected Dimension calSize(Point head, Point tail) {
        Dimension size = new Dimension(Math.abs(head.x - tail.x) + this.padding * 2,
                Math.abs(head.y - tail.y) + this.padding * 2);
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
        setSizeLocation(head, this.tailInParent);
        repaint();
    }

    /**
     * 設定自身尾巴的座標
     * 
     * @param tail
     */
    public void setTailPoint(Point tail) {
        setSizeLocation(this.headInParent, tail);
        repaint();
    }

    /**
     * 根據給定的 direction，更新自身座標與大小
     * 
     * @param point     座標
     * @param direction 頭或尾
     */
    public void setPoint(Point point, boolean direction) {
        if (direction == Direction.HEAD) {
            setHeadPoint(point);
            // System.out.println("Set Head!!");
        } else {
            setTailPoint(point);
            // System.out.println("Set Tail!!");
        }
    }

    /**
     * 根據 pressPoint 與 releasePoint ，將 Line 與 Object 綁定
     * 
     * @param objects
     * @param line
     * @param pressPoint
     * @param releasePoint
     * @return Point[2] 回傳為 {Tail port point, Head port point}
     */
    public Point[] bindLineToObject(Shape[] objects, Point pressPoint, Point releasePoint) {
        Point[] relativePoint = new Point[2];
        int[] direction = new int[2];
        for (int i = 0; i < 2; i++) {
            // 將絕對座標轉換成object的相對座標
            relativePoint[i] = canvas.convertPointToComponent(objects[i], i == 0 ? pressPoint : releasePoint);
            // 依據相對座標計算要添加到哪個 port
            direction[i] = objects[i].getPortDirection(relativePoint[i]);
        }
        // 將連接線的引用傳遞給 object
        objects[0].addLine(this, direction[0], Direction.TAIL);
        objects[1].addLine(this, direction[1], Direction.HEAD);
        // 設置連接線的座標
        Point[] points = { objects[0].getPortLocation(direction[0]),
                objects[1].getPortLocation(direction[1]) };
        return points;
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
        this.type.draw(g, head, tail);
    }
}
