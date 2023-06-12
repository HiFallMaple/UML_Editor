package component.line;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

public class CompositionLineType extends LineType {

    @Override
    protected void drawTail(Graphics2D g2d, Point head, Point tail) {
    }

    @Override
    protected void drawHead(Graphics2D g2d, Point head, Point tail) {
        double angle = Math.atan2(tail.y - head.y, tail.x - head.x);
		double arrowLength = 15;

		// 計算箭頭的的座標
		int[] arrowX = { (int) Math.round(tail.x - arrowLength * Math.cos(angle - Math.PI / 4)),
				tail.x,
				(int) Math.round(tail.x - arrowLength * Math.cos(angle + Math.PI / 4)),
				(int) Math.round(tail.x - arrowLength * (Math.cos(angle - Math.PI / 4) + Math.cos(angle + Math.PI / 4))) };
		int[] arrowY = { (int) Math.round(tail.y - arrowLength * Math.sin(angle - Math.PI / 4)),
				tail.y,
				(int) Math.round(tail.y - arrowLength * Math.sin(angle + Math.PI / 4)),
				(int) Math.round(tail.y - arrowLength * (Math.sin(angle - Math.PI / 4) + Math.sin(angle + Math.PI / 4))) };

		// 繪製箭頭四角形
		g2d.setColor(new Color(0xFFFFFF));
		g2d.fillPolygon(new Polygon(arrowX, arrowY, 4));
		g2d.setColor(this.color);
		g2d.drawPolygon(new Polygon(arrowX, arrowY, 4));
    }
    
}
