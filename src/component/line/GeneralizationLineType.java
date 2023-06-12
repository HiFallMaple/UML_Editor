package component.line;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

public class GeneralizationLineType extends LineType {

    @Override
    protected void drawTail(Graphics2D g2d, Point head, Point tail) {
        
    }

    @Override
    protected void drawHead(Graphics2D g2d, Point head, Point tail) {
        double angle = Math.atan2(tail.y - head.y, tail.x - head.x);
        double arrowLength = 15;

        // 計算箭頭的三個點的座標
        int[] arrowX = { (int) (tail.x - arrowLength * Math.cos(angle - Math.PI / 6)),
                tail.x,
                (int) (tail.x - arrowLength * Math.cos(angle + Math.PI / 6)) };
        int[] arrowY = { (int) (tail.y - arrowLength * Math.sin(angle - Math.PI / 6)),
                tail.y,
                (int) (tail.y - arrowLength * Math.sin(angle + Math.PI / 6)) };

        // 繪製箭頭三角形
        g2d.setColor(new Color(0xFFFFFF));
        g2d.fillPolygon(new Polygon(arrowX, arrowY, 3));
        g2d.setColor(this.color);
        g2d.drawPolygon(new Polygon(arrowX, arrowY, 3));
    }

    
}
