import java.awt.Color;
import java.awt.Point;

import javax.swing.JPanel;

public class ConnectionLine extends JPanel {
    protected Point start;
    protected Point startInParent;
    protected Point end;
    protected Point endInParent;
    protected Point location;
    protected Point size;
    protected Color color;
    protected int padding;
    protected int lineWidth;

    public ConnectionLine(Point start, Point end, Color color, int lineWidth) {
        this.padding = Config.getIntProperty("cl.boxpadding");
        init(start, end, color, lineWidth);
    }

    private void init(Point start, Point end, Color color, int lineWidth) {
        this.location = calLocation(start, end);
        this.size = calSize(start, end);
        this.color = color;
        this.lineWidth = lineWidth;
        this.startInParent = start;
        this.endInParent = end;
        this.start = this.pointMinus(start, this.location);
        this.end = this.pointMinus(end, this.location);
        this.setOpaque(false);
        this._setSize(this.size);
        this.setLocation(location);
    }

    protected void _setSize(Point size) {
        this.setSize(size.x, size.y);
    }

    protected Point calLocation(Point start, Point end) {
        int x = Math.min(start.x, end.x) - this.padding;
        int y = Math.min(start.y, end.y) - this.padding;
        Point location = new Point(x, y);
        return location;
    }

    protected Point calSize(Point start, Point end) {
        Point location = new Point(Math.abs(start.x - end.x) + this.padding * 2,
                Math.abs(start.y - end.y) + this.padding * 2);
        return location;
    }

    protected Point pointMinus(Point a, Point b) {
        return new Point(a.x - b.x, a.y - b.y);
    }

    public void setStartPoint(Point start) {
        init(start, this.endInParent, this.color, this.lineWidth);
        repaint();
    }

    public void setEndPoint(Point end) {
        init(this.startInParent, end, this.color, this.lineWidth);
        repaint();
    }

    public void setPoint(Point point, boolean direction) {
        if (direction == Direction.HEAD) {
            init(this.startInParent, point, this.color, this.lineWidth);
            System.out.println("Set Head!!");
        } else {
            init(point, this.endInParent, this.color, this.lineWidth);
            System.out.println("Set Tail!!");
        }
        repaint();
    }

    public Point getEndPoint() {
        return this.end;
    }

    @Override
    public boolean contains(int x, int y) {
        return false;
    }

}
