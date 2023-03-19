import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class ConnectionLine extends JPanel {
    private Point start;
    private Point end;
    private Point location;
    private Point size;
    private Color color;
    private int padding;
    private int lineWidth;

    public ConnectionLine(Point start, Point end, Color color, int lineWidth) {
        this.padding = Config.getIntProperty("bo.boxpadding");
        init(start, end, color, lineWidth);
    }

    private void init(Point start, Point end, Color color, int lineWidth) {
        this.location = calLocation(start, end);
        this.size = calSize(start, end);
        this.color = color;
        this.lineWidth = lineWidth;
        this.start = this.pointMinus(start, this.location);
        this.end = this.pointMinus(end, this.location);
        this.setOpaque(false);
        this._setSize(this.size);
        this.setLocation(location);
    }
    
    private void _setSize(Point size){
        this.setSize(size.x, size.y);
    }

    private Point calLocation(Point start, Point end){
        int x = Math.min(start.x, end.x) - this.padding;
        int y = Math.min(start.y, end.y) - this.padding;
        // if (x>=this.padding) x -= this.padding;
        // if (y>=this.padding) y -= this.padding;
        Point location = new Point(x, y);
        return location;
    }

    private Point calSize(Point start, Point end){
        Point location = new Point(Math.abs(start.x- end.x)+this.padding*2, Math.abs(start.y - end.y)+this.padding*2);
        return location;
    }

    private Point pointMinus(Point a, Point b){
        return new Point(a.x - b.x, a.y-b.y);
    }

    public void setStartPoint(Point start){
        init(start, this.end, this.color, this.lineWidth);
        repaint();
    }

    public void setEndPoint(Point end){
        init(this.start, end, this.color, this.lineWidth);
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this._setSize(this.size);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        // 計算箭頭的角度和長度
        double angle = Math.atan2(end.y - start.y, end.x - start.x);
        double arrowLength = 15;

        // 計算箭頭的三個點的座標
        int[] arrowX = {(int) (end.x - arrowLength * Math.cos(angle - Math.PI / 6)),
                        end.x,
                        (int) (end.x - arrowLength * Math.cos(angle + Math.PI / 6))};
        int[] arrowY = {(int) (end.y - arrowLength * Math.sin(angle - Math.PI / 6)),
                        end.y,
                        (int) (end.y - arrowLength * Math.sin(angle + Math.PI / 6))};

        // 繪製箭頭線段和箭頭三角形
        g2d.drawLine(start.x, start.y, end.x, end.y);
        g2d.setColor(new Color(0xFFFFFF));
        g2d.fillPolygon(new Polygon(arrowX, arrowY, 3));
        g2d.setColor(this.color);
        g2d.drawPolygon(new Polygon(arrowX, arrowY, 3));
    }
}
