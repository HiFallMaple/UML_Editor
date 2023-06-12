package component.line;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

public abstract class LineType {
    Color color;
    
    protected void draw(Graphics g, Point head, Point tail){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(this.color);
        this.drawLine(g2d, head, tail);
        this.drawHead(g2d, head, tail);
        this.drawTail(g2d, head, tail);
    }

    protected abstract void drawTail(Graphics2D g2d, Point head, Point tail);
    protected abstract void drawHead(Graphics2D g2d, Point head, Point tail);
    protected void drawLine(Graphics2D g2d, Point head, Point tail){
        g2d.drawLine(head.x, head.y, tail.x, tail.y);
    }
}
